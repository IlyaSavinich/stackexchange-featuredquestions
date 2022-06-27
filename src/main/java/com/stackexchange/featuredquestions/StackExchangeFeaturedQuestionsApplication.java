package com.stackexchange.featuredquestions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StackExchangeFeaturedQuestionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StackExchangeFeaturedQuestionsApplication.class, args);
    }

}
