package com.mila.servidor;
// Clase principal de la aplicación Spring Boot, punto de entrada del servidor
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Anotación que indica que esta es una aplicación Spring Boot
@SpringBootApplication
public class ServidorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServidorApplication.class, args);
    }
}