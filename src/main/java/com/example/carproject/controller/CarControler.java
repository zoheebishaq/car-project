package com.example.carproject.controller;

import com.example.carproject.payload.CarDto;
import com.example.carproject.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CarControler {
    private CarService carService;

    public CarControler(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/categorys/{categoryId}/cars")
    public ResponseEntity<CarDto> createCar(@PathVariable(value = "categoryId") long categoryId, @RequestBody CarDto carDto){
        return new ResponseEntity<>(carService.createCar(categoryId,carDto), HttpStatus.CREATED);
    }

    @GetMapping("/categorys/{categoryId}/cars")
    public List<CarDto> getCarByCategoryId(@PathVariable(value = "categoryId")long categoryId){
        return carService.getCarByCategoryId(categoryId);
    }
    @GetMapping("/categorys/{categoryId}/cars/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable(value = "categoryId") Long categoryId,@PathVariable(value = "id")Long carId){
        CarDto carDto = carService.getCarById(categoryId,carId);
        return new ResponseEntity<>(carDto, HttpStatus.OK);
    }
    @PutMapping("/categorys/{categoryId}/cars/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable(value = "categoryId") Long categoryId,@PathVariable(value = "id")Long carId,@RequestBody CarDto carDto){
        CarDto updateCar = carService.updateCar(categoryId,carId,carDto);
        return new ResponseEntity<>(updateCar,HttpStatus.OK);
    }
    @DeleteMapping("/categorys/{categoryId}/cars/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable(value = "categoryId") Long categoryId,@PathVariable(value = "id")Long carId){
        carService.deleteCar(categoryId,carId);
        return new ResponseEntity<>("Car deleted successfully",HttpStatus.OK);
    }
}
