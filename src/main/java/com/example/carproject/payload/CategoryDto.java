package com.example.carproject.payload;

import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {
    private long id;
    private String name;
    private Set<CarDto> cars;
}
