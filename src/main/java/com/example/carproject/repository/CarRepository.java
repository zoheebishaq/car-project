package com.example.carproject.repository;

import com.example.carproject.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByCategoryId(long categoryId);
}
