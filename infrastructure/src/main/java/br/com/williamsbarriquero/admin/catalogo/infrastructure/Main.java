package br.com.williamsbarriquero.admin.catalogo.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(WebServerConfig.class, args);
    }
}