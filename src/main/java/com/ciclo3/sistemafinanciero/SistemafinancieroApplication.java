package com.ciclo3.sistemafinanciero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude ={SecurityAutoConfiguration.class}) //Quita solicitud de login en pagina pgAdmin
public class SistemafinancieroApplication {

    public static void main(String[] args) {

        SpringApplication.run(SistemafinancieroApplication.class, args);
    }

}
