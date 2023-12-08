package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Movie {
    @Id
    private String id;
    private String name;
    private String directorFirstName;
    private String directorLastName;
}
