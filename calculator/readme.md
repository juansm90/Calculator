Calculadora creada como RESTapi creada con Java 8 y Spring Boot.

REQUISITOS

Java 8
Maven
Spring Boot

EJECUCIÓN
Como es una aplicación en Spring Boot solo es necesario ejecutar el CalculatorApplication y a través de Postman en la dirección http://localhost:8080/calculate añadir el parametro de "calculation" y la equación a realizar.

Como la url esta codificada la equacion de suma se tiene que cambiar por %2B, por ejemplo, 2+2 es 2%2B2, el resto de operacion son normales.

Si da error de puerto al iniciar, en application.properties poner server.port = 8090 por ejemplo