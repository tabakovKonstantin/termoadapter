package ru.tabakov.termoadapter;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TermoadapterApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TermoadapterApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
