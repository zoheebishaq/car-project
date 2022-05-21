package com.example.carproject.controller;

import com.example.carproject.payload.CategoryDto;
import com.example.carproject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/api/v1/categorys")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/categorys")
    public List<CategoryDto> getallCategory(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/api/v1/categorys/{id}")
    public ResponseEntity<CategoryDto> GetCategoryById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @PutMapping("/api/v1/categorys/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable long id){
        CategoryDto categoryResponse = categoryService.updateCategory(categoryDto,id);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }
    @DeleteMapping("/api/v1/categorys/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category entity deleted succefully.",HttpStatus.OK);
    }

}
