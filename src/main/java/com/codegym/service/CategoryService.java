package com.codegym.service;

import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    void save(Category category);

    void remove(Long id);

    Category findById(Long id);
}
