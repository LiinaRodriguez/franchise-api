terraform {
  required_version = ">= 1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

# Data source para AMI Ubuntu
data "aws_ami" "ubuntu" {
  most_recent = true
  owners      = ["099720109477"]

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-*-amd64-server-*"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }
}

# --- SECURITY GROUPS (Sin cambios) ---

resource "aws_security_group" "ec2" {
  name = "${var.app_name}-ec2-sg"

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "rds" {
  name = "${var.app_name}-rds-sg"

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [aws_security_group.ec2.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# --- RDS MySQL ---

resource "aws_db_instance" "mysql" {
  identifier     = "${var.app_name}-db"
  engine         = "mysql"
  engine_version = "5.7" # Se mantiene según tu archivo original 
  instance_class = "db.t3.micro"

  db_name  = "franchise_db"
  username = var.db_username
  password = var.db_password

  allocated_storage       = 20
  storage_type            = "gp2"
  skip_final_snapshot     = true
  publicly_accessible     = true
  vpc_security_group_ids  = [aws_security_group.rds.id]

  backup_retention_period = 7
  backup_window           = "03:00-04:00"
  maintenance_window      = "mon:04:00-mon:05:00"
}

resource "aws_iam_role" "ec2_role" {
  name = "${var.app_name}-ec2-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = { Service = "ec2.amazonaws.com" }
    }]
  })
}

# Se adjuntan las políticas gestionadas oficiales (Más robustas)
resource "aws_iam_role_policy_attachment" "ssm_managed" {
  role       = aws_iam_role.ec2_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_role_policy_attachment" "ecr_read" {
  role       = aws_iam_role.ec2_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

resource "aws_iam_instance_profile" "ec2_profile" {
  name = "${var.app_name}-ec2-profile"
  role = aws_iam_role.ec2_role.name
}


resource "aws_instance" "app" {
  ami                    = data.aws_ami.ubuntu.id
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.ec2.id]
  iam_instance_profile   = aws_iam_instance_profile.ec2_profile.name

  root_block_device {
    volume_size           = 30
    volume_type           = "gp2"
    delete_on_termination = true
  }

  user_data = base64encode(<<-EOF
    #!/bin/bash
    set -e
    
    # Instalación de Docker y dependencias (Versión simplificada para Ubuntu)
    apt-get update
    apt-get install -y docker.io docker-compose
    
    systemctl start docker
    systemctl enable docker
    
    # Agregar usuario ubuntu al grupo docker para evitar sudo en el deploy [cite: 14]
    usermod -aG docker ubuntu
    
    # Asegurar que el agente SSM esté activo (Viene preinstalado en Ubuntu)
    systemctl enable amazon-ssm-agent
    systemctl start amazon-ssm-agent
    
    mkdir -p /home/ubuntu/franchise-api
    chown -R ubuntu:ubuntu /home/ubuntu/franchise-api
  EOF
  )

  depends_on = [aws_db_instance.mysql]

  tags = {
    Name = "${var.app_name}-ec2"
  }
}

# Elastic IP
resource "aws_eip" "app" {
  instance = aws_instance.app.id
  domain   = "vpc"

  tags = {
    Name = "${var.app_name}-eip"
  }
}

# ECR Repository
resource "aws_ecr_repository" "app" {
  name         = var.app_name
  force_delete = true

  tags = {
    Name = "${var.app_name}-ecr"
  }
}


output "ec2_instance_id" {
  value = aws_instance.app.id
}

output "rds_host" {
  value = aws_db_instance.mysql.address
}

output "ecr_repository_url" {
  value = aws_ecr_repository.app.repository_url
}