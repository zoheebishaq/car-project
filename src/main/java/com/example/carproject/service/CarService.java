package com.example.carproject.service;

import com.example.carproject.payload.CarDto;

import java.util.List;

public interface CarService {
    CarDto createCar(long categoryId, CarDto carDto);
    List<CarDto> getCarByCategoryId(long categoryId);
    CarDto getCarById(Long categoryId, Long carId);
    CarDto updateCar(Long categoryId, Long carId, CarDto carRequest);
    void deleteCar(Long categoryId, Long carId);
}
