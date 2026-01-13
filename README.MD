# Franchise API

API REST reactiva para la gestión integral de franquicias, sucursales y productos. Construida con **Spring Boot 3.5.9**, **Spring WebFlux** y **R2DBC**, con soporte para bases de datos MySQL reactivas.

## Tabla de Contenidos

- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Despliegue](#despliegue)
  - [Opción 1: Local con Docker Compose](#opción-1-despliegue-local-con-docker-compose)
  - [Opción 2: AWS con Terraform](#opción-2-despliegue-en-aws-con-terraform)
  - [Solución Actual en AWS](#solución-actual-en-aws)
- [Ejecución de Tests](#ejecución-de-tests)
- [Documentación API](#documentación-api)
- [Estructura del Proyecto](#estructura-del-proyecto)

---

## Características

✅ **Arquitectura Reactiva**: Implementada con Spring WebFlux para máximo rendimiento  
✅ **Base de Datos Reactiva**: MySQL con R2DBC para operaciones no bloqueantes  
✅ **API REST**: Endpoints completamente funcionales para CRUD de franquicias, sucursales y productos  
✅ **Documentación Interactiva**: Swagger UI integrado (OpenAPI 3.0)  
✅ **Actuator**: Métricas y salud de la aplicación incluidas  
✅ **Tests Automatizados**: Suite completa de pruebas unitarias e integración  
✅ **Dockerizado**: Imagen multi-stage  
✅ **Infraestructura como Código**: Terraform para AWS  

---

## Requisitos Previos

### Para Ejecución Local con Docker Compose

- **Docker 20.10+**
  - [Descargar Docker](https://www.docker.com/products/docker-desktop)

- **Docker Compose 2.0+**
  - Incluido en Docker Desktop

- **Git** para clonar el repositorio

### Para Despliegue en AWS

- **Terraform 1.0+**
  - [Descargar Terraform](https://www.terraform.io/downloads.html)

- **AWS CLI** configurado con credenciales válidas
  - [Instalar AWS CLI](https://aws.amazon.com/cli/)

- Cuenta activa en **AWS**

---

## Despliegue

### Opción 1: Despliegue Local con Docker Compose

La ejecución local se realiza con **Docker Compose**, que levanta la aplicación y la base de datos en contenedores.

#### Pasos para Desplegar Localmente

```bash
# 1. Clonar el repositorio
git clone https://github.com/liinarodriguez/franchise-api.git
cd franchise-api

# 2. Levantar los contenedores
docker-compose up -d --build

# 3. Verificar que está ejecutándose
docker-compose ps

# 4. Comprobar salud de la aplicación
curl http://localhost:8080/actuator/health
```

#### Acceso Local

| Componente | URL |
|-----------|-----|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/webjars/swagger-ui/index.html#/ |
| Health Check | http://localhost:8080/actuator/health |
| Base de Datos | `localhost:3306` (usuario: `admin`, contraseña: `password`) |

#### Gestión Local

```bash
# Ver logs de la aplicación
docker-compose logs -f app

# Ver logs de la base de datos
docker-compose logs -f mysql

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes (limpia datos)
docker-compose down -v
```

---

### Opción 2: Despliegue en AWS con Terraform

La infraestructura en AWS se gestiona con **Terraform**, que automatiza la creación de EC2, Security Groups, y otros recursos.

#### Prerrequisitos AWS

1. **Instalar Terraform**:
   ```bash
   # Windows (con Chocolatey)
   choco install terraform
   
   # macOS (con Homebrew)
   brew install terraform
   
   # Linux
   wget https://releases.hashicorp.com/terraform/1.0.0/terraform_1.0.0_linux_amd64.zip
   unzip terraform_1.0.0_linux_amd64.zip
   sudo mv terraform /usr/local/bin/
   ```

2. **Configurar AWS CLI**:
   ```bash
   aws configure
   # Ingresar: Access Key ID, Secret Access Key, Región (us-east-1), Formato (json)
   ```

3. **Verificar credenciales**:
   ```bash
   aws sts get-caller-identity
   ```

#### Pasos para Desplegar en AWS

```bash
cd terraform

# 1. Inicializar Terraform (solo primera vez)
terraform init

# 2. Planificar despliegue (ver qué va a crear)
terraform plan -out=tfplan

# 3. Aplicar cambios
terraform apply tfplan

# 4. Ver IP pública asignada
terraform output instance_public_ip
```

#### Configuración de AWS

Edita `terraform/terraform.tfvars`:

```hcl
aws_region        = "us-east-1"           # Región AWS
app_name           = "franchise-api"      # Nombre de la aplicación
environment        = "production"         # Entorno
instance_type      = "t3.medium"          # Tipo de instancia EC2
```

#### Destruir la Infraestructura

```bash
cd terraform

# Destruir todos los recursos de AWS
terraform destroy

# Confirmar escribiendo: yes
```

---

### Solución Actual en AWS

La aplicación está **actualmente desplegada** en AWS. Accede a través de las siguientes URLs:

| Componente | URL |
|-----------|-----|
| **API Base** | http://34.237.156.227:8080 |
| **Swagger UI** | http://34.237.156.227:8080/webjars/swagger-ui/index.html#/ |
| **Health Check** | http://34.237.156.227:8080/actuator/health |

#### Información de la Instancia

```
Dirección IP Pública: 34.237.156.227
Región: us-east-1
Puerto: 8080
```

#### Ejemplo de Uso en AWS

```bash
# Verificar salud
curl http://34.237.156.227:8080/actuator/health

# Crear una franquicia
curl -X POST http://34.237.156.227:8080/api/v1/franchises \
  -H "Content-Type: application/json" \
  -d '{"name":"Mi Franquicia"}'
```

---

## Ejecución de Tests

### Ejecutar Todos los Tests

```bash
./mvnw test
```

### Ejecutar Tests de una Clase Específica

```bash
./mvnw test -Dtest=CreateFranchiseUseCaseTest
```

### Ejecutar con Coverage (Cobertura de Código)

```bash
./mvnw clean test jacoco:report
# Reporte disponible en: target/site/jacoco/index.html
```

### Ejecutar Solo Tests de Integración

```bash
./mvnw test -Dtest=ControllerIntegrationTest
```

### Suite de Tests Disponible

El proyecto incluye tests para:

- ✅ **Modelos de Dominio**: Franquicia, Sucursal, Producto
- ✅ **Casos de Uso**: Creación, actualización, eliminación de entidades
- ✅ **Controladores REST**: Integración end-to-end
- ✅ **Reactividad**: Tests con Reactor y WebFlux

Reportes de test generados en: `target/surefire-reports/`

---

## Documentación API

### Acceder a Swagger UI

**Local**: http://localhost:8080/webjars/swagger-ui/index.html#/

**AWS**: http://34.237.156.227:8080/webjars/swagger-ui/index.html#/

### Health Check

**Local**:
```bash
curl http://localhost:8080/actuator/health
```

**AWS**:
```bash
curl http://34.237.156.227:8080/actuator/health
```

### Endpoints Implementados

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **POST** | `/api/v1/franchises` | Crear nueva franquicia |
| **PUT** | `/api/v1/franchises/{franchiseId}/name` | Actualizar nombre de franquicia |
| **GET** | `/api/v1/franchises/{franchiseId}/top-stock-products` | Obtener productos con mayor stock de una franquicia |
| **POST** | `/api/v1/franchises/{franchiseId}/branches` | Agregar sucursal a una franquicia |
| **PUT** | `/api/v1/branches/{branchId}/name` | Actualizar nombre de sucursal |
| **POST** | `/api/v1/branches/{branchId}/products` | Agregar producto a una sucursal |
| **PUT** | `/api/v1/products/{productId}/stock` | Actualizar stock de un producto |
| **PUT** | `/api/v1/products/{productId}/name` | Actualizar nombre de un producto |
| **DELETE** | `/api/v1/products/{productId}` | Eliminar un producto |

---

## Estructura del Proyecto

```
franchise-api/
├── src/
│   ├── main/
│   │   ├── java/com/franchise/api/
│   │   │   ├── domain/           # Entidades del dominio (Franquicia, Sucursal, Producto)
│   │   │   ├── application/      # Casos de uso (Use Cases)
│   │   │   ├── infra/            # Adaptadores REST, persistencia
│   │   │   │   ├── adapter/
│   │   │   │   │   ├── persistence/  # Acceso a datos (R2DBC)
│   │   │   │   │   └── rest/     # Controladores HTTP
│   │   │   └── FranchiseApiApplication.java
│   │   └── resources/
│   │       ├── application.yaml  # Configuración Spring
│   │       └── schema.sql        # Script de inicialización BD
│   └── test/
│       └── java/com/franchise/api/ # Tests unitarios e integración
├── docker-compose.yml              # Compose para ejecución local
├── docker-compose.prod.yml         # Compose para producción
├── Dockerfile                      # Imagen Docker multi-stage
├── terraform/
│   ├── main.tf                     # Recursos principales AWS (EC2, Security Groups)
│   ├── variables.tf                # Variables de Terraform
│   ├── terraform.tfvars            # Valores de configuración
│   └── terraform.tfstate           # Estado de la infraestructura
├── pom.xml                         # Dependencias Maven
└── README.md                       # Readme
```