package com.nttdata.indhub.controller;

import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.CategoryRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;

public interface CategoryControllerRest {

  NetflixResponse<D4iPageRest<CategoryRest>> getAllCategories(int page, int size, Pageable pageable) throws NetflixException;

  NetflixResponse<CategoryRest> getCategoryById(Long id) throws NetflixException;

  NetflixResponse<CategoryRest> createCategory(CategoryRest category) throws NetflixException;

  NetflixResponse<CategoryRest> updateCategory(CategoryRest category) throws NetflixException;

  NetflixResponse<Object> deleteCategory(Long id) throws NetflixException;
}
