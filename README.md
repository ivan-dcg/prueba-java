# prueba-java
Prueba en java para gestión de usuario

# especificaciones
project con maven
spring boot 3.4.5
Java 17

# Script para BD
# Schema
CREATE SCHEMA `prueba` DEFAULT CHARACTER SET utf8;

# Table
CREATE TABLE `prueba`.`users` (
`id` INT NOT NULL AUTO_INCREMENT,
`username` VARCHAR(45) NOT NULL,
`password` VARCHAR(300) NOT NULL,
PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

# URL Swagger
{dominio}/doc/swagger-ui/index.html

# Ejecución
Clona el repositorio del proyecto
Ejecuta mvn spring-boot:run