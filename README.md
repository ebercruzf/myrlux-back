# MyrluxBack

## Descripción
MyrluxBack es una aplicación backend para la gestión de alumnos en una institución educativa. Proporciona una API RESTful reactiva para crear, leer, actualizar y eliminar información de alumnos.

## Tecnologías Principales
- Java 21
- Spring Boot 3.3.0
- Spring WebFlux
- PostgreSQL
- Spring Data JPA
- Maven
- SpringDoc OpenAPI 2.3.0
- Jakarta Validation
- ModelMapper 2.4.4
- JUnit 5.8.2 y Mockito 4.5.1 para pruebas
- Spring Boot Actuator

## Requisitos Previos
- JDK 21
- Maven 3.x
- PostgreSQL
- NOTA se uso en un inicio Intellij IDEA 2 : 2022.2.5
- Fnalmente se ajusto para usarse con Intellij IDEA CE : 2024.1.4

## Configuración
1. Clona el repositorio:
   ```
   git clone https://github.com/tu-usuario/myrlux-back.git
   ```
2. Navega al directorio del proyecto:
   ```
   cd myrlux-back
   ```
3. Configura la base de datos en `src/main/resources/application.yml`

## Ejecución
Para ejecutar la aplicación:
```
mvn spring-boot:run


```
Comando extras de utilidad:

```
mvn clean install

mvn spring-boot:run -Dspring-boot.run.profiles=dev

```


La aplicación estará disponible en `http://localhost:11002`

## Endpoints API
- `GET /api/home`: Página de inicio
- `POST /api/crear/alumno`: Crear un nuevo alumno
- `GET /api/lista/alumno`: Obtener todos los alumnos
- `GET /api/obtener/alumno/{id}`: Obtener un alumno por ID
- `PUT /api/actualizar/alumno/{id}`: Actualizar un alumno
- `DELETE /api/eliminar/alumno/{id}`: Eliminar un alumno

## Documentación API
La documentación de la API está disponible en `http://localhost:11002/swagger-ui.html`

## Monitoreo
Los endpoints de Actuator están disponibles en `http://localhost:9090/actuator`

## Pruebas
Para ejecutar las pruebas:
```
mvn test
```

## Características Principales
- API RESTful Reactiva
- Persistencia de datos con JPA
- Validación de datos
- Documentación de API con Swagger
- Monitoreo con Actuator
- Pruebas unitarias
- Base de datos en Postgres

## Estructura del Proyecto
- `src/main/java/com/ebercruz/myrlux/back/`
    - `api/`: Controladores y servicios
    - `config/`: Configuraciones de Spring
    - `dto/`: Objetos de transferencia de datos
    - `entity/`: Entidades JPA
    - `repository/`: Repositorios de datos
    - `util/`: Clases utilitarias
- `src/main/resources/`
    - `bd/`: Script, insert para crear y cargar la base
    - `config/`: Configuraciones de Spring
    - `postman/`: colecciones de postman para importar 

## Contribución

Una vez desplegado puede acceder a las siguientes url.

http://localhost:9090/actuator

http://localhost:9090/actuator/health

http://localhost:11002/webjars/swagger-ui/index.html#/alumno-controller/home

http://localhost:11002/v3/api-docs

## Licencia
Este proyecto está licenciado bajo la Licencia  - vea el archivo LICENSE.md para más detalles.