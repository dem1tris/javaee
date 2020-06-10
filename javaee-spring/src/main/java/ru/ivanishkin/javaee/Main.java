package ru.ivanishkin.javaee;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
class Task3 {
}

public class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Task3.class).run();
    }
}
