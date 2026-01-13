

## Despliegue Local (Docker)

Sigue estos pasos para levantar la infraestructura completa de forma automática:

**1. Clonar repositorio**

```bash
git clone https://github.com/liinarodriguez/franchise-api.git
cd franchise-api

```

**2. Construir y levantar contenedores**

```bash
docker-compose up --build -d
```


## Ejecutar tests

Este proyecto utiliza Maven y JUnit 5 para el testing

```bash
./mvnw test
