package com.example.carproject.service.impl;

import com.example.carproject.entity.Category;
import com.example.carproject.exception.ResourceNotFoundException;
import com.example.carproject.payload.CategoryDto;
import com.example.carproject.repository.CategoryRepository;
import com.example.carproject.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;
    private CarServiceImpl carService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper, CarServiceImpl carService) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.carService = carService;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapToEnity(categoryDto);
        Category newCategory = categoryRepository.save(category);

        CategoryDto categoryResponse = mapToDto(newCategory);
        return categoryResponse;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        return category.stream().map(cat -> mapToDto(cat)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return mapToDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long id) {
        //get category by id from database
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setName(categoryDto.getName());
        Category updateCategory = categoryRepository.save(category);
        return mapToDto(updateCategory);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }

    //convert Entity into DTO

    private CategoryDto mapToDto(Category category) {
//        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        categoryDto.setCars(category.getCars().stream().map(carService::maptoDto).collect(Collectors.toSet()));
        categoryDto.setCars(category.getCars().stream().map(car -> carService.maptoDto(car)).collect(Collectors.toSet()));

        return categoryDto;
    }

    private Category mapToEnity(CategoryDto categoryDto) {
//        Category category = mapper.map(categoryDto, Category.class);
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setCars(categoryDto.getCars().stream().map(carService::mapToEntity).collect(Collectors.toSet()));
        category.setCars(categoryDto.getCars().stream().map(carDto -> carService.mapToEntity(carDto)).collect(Collectors.toSet()));
        return category;
    }
}
