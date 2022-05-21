package com.example.carproject.service.impl;

import com.example.carproject.entity.Car;
import com.example.carproject.entity.Category;
import com.example.carproject.exception.CarProjectAPIException;
import com.example.carproject.exception.ResourceNotFoundException;
import com.example.carproject.payload.CarDto;
import com.example.carproject.repository.CarRepository;
import com.example.carproject.repository.CategoryRepository;
import com.example.carproject.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CarServiceImpl(CarRepository carRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CarDto createCar(long categoryId, CarDto carDto) {
        Car car = mapToEntity(carDto);
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        car.setCategory(category);

        Car newCar = carRepository.save(car);
        return maptoDto(newCar);
    }

    @Override
    public List<CarDto> getCarByCategoryId(long categoryId) {
        // retrieve cars by catgoryId
        List<Car> cars = carRepository.findByCategoryId(categoryId);
        // convert list of car entities to list of car dto's
        return cars.stream().map(car -> maptoDto(car)).collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Long categoryId, Long carId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        Car car = carRepository.findById(carId).orElseThrow(
                () -> new ResourceNotFoundException("Car", "id", carId));

        if (!car.getCategory().getId().equals(category.getId())) {
            throw new CarProjectAPIException(HttpStatus.BAD_REQUEST, "Car does not belong to category");
        }

        return maptoDto(car);
    }

    @Override
    public CarDto updateCar(Long categoryId, Long carId, CarDto carRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        Car car = carRepository.findById(carId).orElseThrow(
                () -> new ResourceNotFoundException("Car", "id", carId));

        if (!car.getCategory().getId().equals(category.getId())) {
            throw new CarProjectAPIException(HttpStatus.BAD_REQUEST, "Car does not belong to category");
        }
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());

        Car updatedCar = carRepository.save(car);

        return maptoDto(updatedCar);
    }

    @Override
    public void deleteCar(Long categoryId, Long carId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        Car car = carRepository.findById(carId).orElseThrow(
                () -> new ResourceNotFoundException("Car", "id", carId));

        if (!car.getCategory().getId().equals(category.getId())) {
            throw new CarProjectAPIException(HttpStatus.BAD_REQUEST, "Car does not belong to category");
        }
        carRepository.delete(car);
    }


    public CarDto maptoDto(Car car) {
        CarDto carDto = mapper.map(car, CarDto.class);
//        CarDto carDto = new CarDto();
//        carDto.setId(car.getId());
//        carDto.setBrand(car.getBrand());
//        carDto.setModel(car.getModel());
        return carDto;
    }

    public Car mapToEntity(CarDto carDto) {
        Car car = mapper.map(carDto, Car.class);
//        Car car = new Car();
//        car.setId(carDto.getId());
//        car.setBrand(carDto.getBrand());
//        car.setModel(carDto.getModel());
        return car;
    }

}
