package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
public class Movie {
    @PrimaryKey
    private UUID id;
    private String name;
    private String directorFirstName;
    private String directorLastName;
}
