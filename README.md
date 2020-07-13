## Spring-Boot Api Rest Automoviles

Proyecto rest creado con spring-boot y desplegado en Google Cloud App-Engine

* Java 8
* Maven 3.6.0
* Spring-boot 2.2.5
* App Engine 2.3.0
* Mysql 5.7

### Swagger Api Doc
Documentación api: [LINK](https://api-rest-auto.rj.r.appspot.com/swagger-ui.html)
### Ambiente local
* Necesitamos tener instalado docker-compose [Docker-Compose-Install](https://docs.docker.com/compose/install/)
* Levantar Mysql corriendo el comando `docker-compose up` en la carpeta raíz del proyecto.
* Desde la terminal correr `mvn spring-boot:run`
### Ambiente dev 
Para desplegar la aplicación en una instancia de google cloud necesitamos tener instalado el [SDK de Google Cloud](https://cloud.google.com/sdk/install) y un usuario con los roles necesarios para poder desplegar la aplicacion.

Desplegar la app: `mvn clean package appengine:deploy`

### Comandos Útiles
* Ejecutar test's unitarios: `mvn surefire:test`
* Ejecutar test's de integración: `mvn failsafe:integration-test`
* Ver logs de la aplicación desplegada en la instancia de google cloud: `gcloud app logs tail -s default`