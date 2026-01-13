# Franchise API

API REST reactiva para la gestión integral de franquicias, sucursales y productos. Construida con **Spring Boot 3.5.9**, **Spring WebFlux** y **R2DBC**, con soporte para bases de datos MySQL reactivas.

## Tabla de Contenidos

- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Instalación Local](#instalación-local)
- [Ejecución Local](#ejecución-local)
- [Ejecución de Tests](#ejecución-de-tests)
- [Documentación API](#documentación-api)
- [Despliegue](#despliegue)
  - [Docker y Docker Compose](#docker-y-docker-compose)
  - [AWS con Terraform](#aws-con-terraform)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Variables de Entorno](#variables-de-entorno)
- [Contribución](#contribución)

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

### Ejecución Local

- **Java 17** o superior
  - [Descargar JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  - O usar herramienta como [SDKMAN](https://sdkman.io/)

- **Maven 3.8.1** o superior (incluido: `./mvnw`)
  
- **MySQL 8.0** o superior
  - [Descargar MySQL](https://dev.mysql.com/downloads/mysql/)
  - O usar Docker: `docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0`

- **Git** para clonar el repositorio

### Ejecución con Docker

- **Docker 20.10+**
- **Docker Compose 2.0+**

### Despliegue en AWS

- **Terraform 1.0+**
- **AWS CLI** configurado con credenciales válidas
- Cuenta activa en **AWS**

---

## Instalación Local

### 1. Clonar el Repositorio

```bash
git clone https://github.com/liinarodriguez/franchise-api.git
cd franchise-api
```

### 2. Ejecución local con docker

```bash
 docker-compose up -d --build  
```

### Verificar que está Ejecutándose

```bash
# Comprueba que el servidor responde
curl http://localhost:8080/actuator/health

# Respuesta esperada:
# {"status":"UP"}
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

Cuando la aplicación está ejecutándose:

```
http://localhost:8080/swagger-ui.html
```

### Acceder a OpenAPI JSON

```
http://localhost:8080/v3/api-docs
```

### Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `/franchises` | Listar todas las franquicias |
| **POST** | `/franchises` | Crear nueva franquicia |
| **GET** | `/franchises/{id}` | Obtener franquicia por ID |
| **PUT** | `/franchises/{id}` | Actualizar nombre franquicia |
| **GET** | `/franchises/{id}/branches` | Listar sucursales de franquicia |
| **POST** | `/franchises/{id}/branches` | Agregar sucursal a franquicia |
| **POST** | `/branches/{id}/products` | Agregar producto a sucursal |
| **PUT** | `/products/{id}` | Actualizar stock de producto |
| **DELETE** | `/products/{id}` | Eliminar producto |

---

## Despliegue

### Docker y Docker Compose

#### Despliegue Local con Docker Compose

```bash
# Construir y levantar los contenedores
docker-compose up --build -d

# Verificar que está ejecutándose
docker-compose ps

# Ver logs
docker-compose logs -f app

# Detener servicios
docker-compose down

# Limpiar todo (volúmenes incluidos)
docker-compose down -v
```

**Acceso**:
- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Base de Datos: `localhost:3306` (usuario: `admin`, contraseña: `password`)

#### Despliegue Producción con Docker Compose

Usa el archivo de configuración de producción:

```bash
docker-compose -f docker-compose.prod.yml up --build -d
```

---

### AWS con Terraform

#### Prerrequisitos AWS

1. **Instalar Terraform**:
   ```bash
   # En Windows (con Chocolatey)
   choco install terraform
   
   # En macOS (con Homebrew)
   brew install terraform
   
   # En Linux
   wget https://releases.hashicorp.com/terraform/1.0.0/terraform_1.0.0_linux_amd64.zip
   unzip terraform_1.0.0_linux_amd64.zip
   sudo mv terraform /usr/local/bin/
   ```

2. **Configurar AWS CLI**:
   ```bash
   aws configure
   # Ingresar Access Key ID
   # Ingresar Secret Access Key
   # Región por defecto (ej: us-east-1)
   # Formato de salida: json
   ```

3. **Verificar credenciales**:
   ```bash
   aws sts get-caller-identity
   ```

#### Desplegar en AWS

```bash
cd terraform

# Inicializar Terraform (solo primera vez)
terraform init

# Planificar despliegue (ver qué va a crear)
terraform plan -out=tfplan

# Aplicar cambios
terraform apply tfplan

# Ver outputs (IP pública, etc.)
terraform output

# Ver estado actual
terraform show
```

#### Parámetros Configurables

Edita `terraform/terraform.tfvars`:

```hcl
aws_region        = "us-east-1"           # Región AWS
app_name           = "franchise-api"      # Nombre de la aplicación
environment        = "production"         # Entorno
instance_type      = "t3.medium"          # Tipo de instancia EC2
```



#### Acceder a la Aplicación en AWS

Después del despliegue:

```bash
# Obtener IP pública
terraform output instance_public_ip

# Acceder a la API
curl http://<IP_PÚBLICA>:8080/actuator/health

# Acceder a Swagger
http://<IP_PÚBLICA>:8080/swagger-ui.html
```

#### Destruir la Infraestructura

```bash
terraform destroy
# Confirmar escribiendo: yes
```

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
|   |   |   |   |   ├── persistence # acceso a datos
│   │   │   │   │   └── rest/     # Controladores HTTP
│   │   │   └── FranchiseApiApplication.java
│   │   └── resources/
│   │       ├── application.yaml  # Configuración Spring
│   │       └── schema.sql        # Script de inicialización BD
│   └── test/
│       └── java/com/franchise/api/ # Tests unitarios e integración
├── Dockerfile               # Imagen Docker multi-stage
├── docker-compose.yml       # Compose desarrollo
└── docker-compose.prod.yml  # Compose producción
├── terraform/
│   ├── main.tf                  # Recurso principales AWS
├── pom.xml                      # Dependencias Maven
└── README.md                    # Documentacion rápida del proyecto
```