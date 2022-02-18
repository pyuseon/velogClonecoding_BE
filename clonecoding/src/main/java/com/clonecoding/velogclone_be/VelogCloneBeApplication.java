package com.clonecoding.velogclone_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VelogCloneBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VelogCloneBeApplication.class, args);
    }

}
