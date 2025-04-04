package com.nttdata.indhub.service;

import com.nttdata.indhub.controller.model.rest.CategoryRest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.exception.NetflixException;

public interface CategoryService {

  Page<CategoryRest> getAllCategories(Pageable pageable) throws NetflixException;

  CategoryRest getCategoryById(Long id) throws NetflixException;

  CategoryRest createCategory(CategoryRest category) throws NetflixException;

  CategoryRest updateCategory(CategoryRest category, Long id) throws NetflixException;

  void deleteCategory(Long id) throws NetflixException;
}