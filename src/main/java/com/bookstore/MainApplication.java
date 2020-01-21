package com.bookstore;

import com.bookstore.service.BookstoreService;
import java.util.List;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.bookstore.dto.BookstoreDto;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            List<BookstoreDto> authors = bookstoreService.fetchAuthors();
            authors.forEach(a -> System.out.println(a.getAuthor()
                    + ", Title: " + a.getTitle()));
        };
    }
}

/*
 * Avoid Entity In DTO Via Constructor Expression (no association)

Description: Let's assume that we have two entities, Author and Book. There is no materialized association between them, but, both entities shares an attribute named, genre. We want to use this attribute to join the tables corresponding to Author and Book, and fetch the result in a DTO. The result should contain the Author entity and only the title attribute from Book. Well, when you are in a scenario as here, it is strongly advisable to avoid fetching the DTO via constructor expression. This approach cannot fetch the data in a single SELECT, and is prone to N+1. Way better than this consists of using Spring projections, JPA Tuple or even Hibernate ResultTransformer. These approaches will fetch the data in a single SELECT. This application is a DON'T DO THIS example. Check the number of queries needed for fetching the data. In place, do it as here: Entity Inside Spring Projection (no association).


 * 
 */
 
