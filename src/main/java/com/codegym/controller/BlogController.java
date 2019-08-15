package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.service.BlogService;
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
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public ResponseEntity<Page<Blog>>listBlog(Pageable pageable){
        Page<Blog>blogs = blogService.findAll(pageable);
        if (blogs.getTotalElements() == 0){
            return  new ResponseEntity<Page<Blog>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Blog>>(blogs,HttpStatus.OK);
    }

    @RequestMapping(value = "/blogs/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog>getBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        if (blog == null){
            return  new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Blog>(blog,HttpStatus.OK);
    }

    @RequestMapping(value = "/blogs",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void>createBlog(@RequestBody Blog blog, UriComponentsBuilder builder){
        blogService.save(blog);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/blogs/{id}").buildAndExpand(blog.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/blogs/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Blog>updateBlog(@PathVariable Long id, @RequestBody Blog blog){
        Blog currentBlog = blogService.findById(id);
        if (currentBlog == null){
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        currentBlog.setCategory(blog.getCategory());
        currentBlog.setContent(blog.getContent());
        currentBlog.setId(blog.getId());

        blogService.save(currentBlog);
        return new ResponseEntity<Blog>(currentBlog,HttpStatus.OK);
    }

    @RequestMapping(value = "/blogs/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Blog>deleteBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        if (blog == null){
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        blogService.remove(id);
        return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
    }
}
