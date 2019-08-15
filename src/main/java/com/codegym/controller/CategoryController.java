package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Page<Category>> listCategory(Pageable pageable){
        Page<Category>categories = categoryService.findAll(pageable);
        if (categories.getTotalElements() == 0){
            return  new ResponseEntity<Page<Category>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Category>>(categories,HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category>getCategory(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if (category == null){
            return  new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(category,HttpStatus.OK);
    }

    @RequestMapping(value = "/categories",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void>createCategory(@RequestBody Category category, UriComponentsBuilder builder){
        categoryService.save(category);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Category>updateCategory(@PathVariable Long id, @RequestBody Category category){
        Category currentCategory = categoryService.findById(id);
        if (currentCategory == null){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        currentCategory.setBlogs(category.getBlogs());
        currentCategory.setName(category.getName());
        currentCategory.setId(category.getId());

        categoryService.save(currentCategory);
        return new ResponseEntity<Category>(currentCategory,HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category>deleteCategory(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if (category == null){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        categoryService.remove(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
    }


}
