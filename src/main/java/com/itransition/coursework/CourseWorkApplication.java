package com.itransition.coursework;

import com.itransition.coursework.search.index.Indexer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseWorkApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CourseWorkApplication.class, args);
    }

    @Bean
    public ApplicationRunner buildIndex(Indexer indexer) {
        return (ApplicationArguments args) -> {
            indexer.indexPersistedData("com.itransition.coursework.item.Item");
        };
    }
}
