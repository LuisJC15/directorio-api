Directorio API - Sistema de Gestión de Personas y Facturas

API REST desarrollada en **Spring Boot** para la gestión de personas y sus facturas, como parte de una prueba técnica para GetechnologiesMx.

Características Principales

*Requisitos:
- Arquitectura: Spring Boot
- Base de datos: H2 embebida con consola web accesible
- Validaciones: Campos obligatorios, identificación única, apellido materno opcional
- Logging: SLF4J implementado
- Manejo de excepciones: Excepciones personalizadas con `@ControllerAdvice`
- Códigos HTTP: Respuestas apropiadas (200, 201, 404, 409, 500)
- Testing: Tests unitarios

*Funcionalidades Plus Implementadas:
- Docker: Contenedor listo para producción
- CI/CD: Pipeline automático con GitHub Actions
- Paginación: Endpoint `/personas/paginated`

*Estructura del Proyecto:
📁 directorio-api/  (RAÍZ DEL PROYECTO)
│
├── 📁 .github/workflows/    → PIPELINE CI/CD
│   └── 📄 ci.yml            → GitHub Actions - Ejecuta tests, build, Docker automáticamente
│
├── 📁 src/                  → CÓDIGO FUENTE
│   │
│   ├── 📁 main/             → CÓDIGO PRINCIPAL
│   │   │
│   │   ├── 📁 java/com/directorio/
│   │   │   │
│   │   │   ├── 📄 DirectorioApiApplication.java  → Clase main
│   │   │   │
│   │   │   ├── 📁 controller/                    → CONTROLADORES REST
│   │   │   │   ├── 📄 DirectorioRestService.java → Endpoints de Personas
│   │   │   │   └── 📄 FacturaRestService.java    → Endpoints de Facturas
│   │   │   │
│   │   │   ├── 📁 exception/                     → MANEJO GLOBAL DE ERRORES
│   │   │   │   ├── 📄 GlobalExceptionHandler.java → @ControllerAdvice para excepciones
│   │   │   │   ├── 📄 PersonaNotFoundException.java     → Cuando no encuentra persona
│   │   │   │   └── 📄 FacturaNotFoundException.java     → Cuando no encuentra factura
│   │   │   │
│   │   │   ├── 📁 model/                         → MODELOS/ENTIDADES
│   │   │   │   ├── 📄 Persona.java               → Entidad Persona (id, nombre, apellidos, identificación)
│   │   │   │   └── 📄 Factura.java               → Entidad Factura (id, fecha, monto, persona)
│   │   │   │
│   │   │   ├── 📁 repository/                    → PERSISTENCIA DE DATOS
│   │   │   │   ├── 📄 PersonaRepository.java     → JPA Repository para Persona
│   │   │   │   └── 📄 FacturaRepository.java     → JPA Repository para Factura
│   │   │   ├── 📁 service/                       → LÓGICA DE NEGOCIO
│   │   │   │   ├── 📄 DirectorioService.java     → Operaciones con Personas
│   │   │   │   └── 📄 VentasService.java         → Operaciones con Facturas  
│   │   │   │
│   │   └── 📁 resources/                         → CONFIGURACIONES
│   │       └── 📄 application.properties         → Config Spring, H2 database, logging
│   │
│   └── 📁 test/                                  → PRUEBAS
│       │
│       └── 📁 java/com/directorio/
│           ├── 📄 DirectorioApiApplicationTests.java → Test de contexto Spring
│           ├── 📄 DirectorioTest.java                → Tests de Personas
│           └── 📄 VentasTest.java                    → Tests de Facturas
│
├── 📄 Dockerfile               → CONFIGURACIÓN DOCKER - Cómo construir contenedor
├── 📄 pom.xml                  → CONFIGURACIÓN MAVEN - Dependencias, plugins, build
└── 📄 README.md                → DOCUMENTACIÓN - Instrucciones, endpoints, cómo usar
 
*Cómo Ejecutar el Proyecto:

Opción 1: Local con Maven

# 1. Clonar repositorio
git clone https://github.com/LuisJC15/directorio-api.git
cd directorio-api

# 2. Compilar y ejecutar
mvn clean spring-boot:run

# 3. Acceder a la API
# http://localhost:8080

Opción 2: Con Docker 

# 1. Construir imagen
docker build -t directorio-api .

# 2. Ejecutar contenedor
docker run -p 8080:8080 directorio-api

# 3. Ver logs en tiempo real
docker logs -f [container_id]

*Endpoints de la API:
👥 Personas:
Método	Endpoint	Descripción	Código HTTP
@PostMapping	                             → POST /personas	                          (Crear nueva persona	201 Created)
@GetMapping("/{id}")	                     → GET /personas/{id}	                      (Obtener persona por ID	200 OK)
@GetMapping("/identificacion/{identificacion}")	→  GET /personas/identificacion/{identificacion}	(Buscar persona por identificación	200 OK)
@GetMapping	                               → GET /personas	Listar todas las personas	200 OK
@GetMapping("/paginated")	                 → GET /personas/paginated?page=0&size=10   (Listar con paginación	200 OK)
@DeleteMapping("/{identificacion}")	       → DELETE /personas/{identificacion}	      (Eliminar persona por identificación)

🧾 Facturas:
Método	Endpoint	Descripción
@PostMapping("/{identificacionPersona}")   → POST /facturas/{identificacionPersona}    (Crear 201 Created)
@GetMapping("/{identificacionPersona}")    → GET /facturas/{identificacionPersona}     (Listar de una persona 200 OK)

Las pruebas fueron realizadas en POSTMAN.
