package com.codegym.service;

import com.codegym.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<Blog>findAll(Pageable pageable);

    void save(Blog blog);

    void remove(Long id);

    Blog findById(Long id);
}
