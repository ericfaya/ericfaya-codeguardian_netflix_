package com.nttdata.indhub.service.impl;

import com.nttdata.indhub.controller.model.rest.CategoryRest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.exception.NetflixNotFoundException;
import com.nttdata.indhub.exception.error.ErrorDto;
import com.nttdata.indhub.mapper.CategoryMapper;
import com.nttdata.indhub.persistence.entity.CategoryEntity;
import com.nttdata.indhub.persistence.repository.CategoryRepository;
import com.nttdata.indhub.service.CategoryService;
import com.nttdata.indhub.util.constant.ExceptionConstantsUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final CategoryMapper categoryMapper;


  @Override
  public Page<CategoryRest> getAllCategories(Pageable pageable) throws NetflixException {
    Page<CategoryEntity> page = categoryRepository.findAll(pageable);
    return page.map(categoryMapper::mapToRest);
  }

  @Override
  public CategoryRest getCategoryById(Long id) throws NetflixException {
    CategoryEntity category = categoryRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));

    return categoryMapper.mapToRest(category);
  }

  @Override
  public CategoryRest createCategory(CategoryRest category) throws NetflixException {
    CategoryEntity categoryEntity = categoryMapper.mapToEntity(category);
    categoryRepository.save(categoryEntity);
    return category;
  }

  @Override
  public CategoryRest updateCategory(CategoryRest category, Long id) throws NetflixException {
    CategoryEntity categoryEntity = categoryRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    categoryEntity.setName(category.getName());
    categoryRepository.save(categoryEntity);
    return categoryMapper.mapToRest(categoryEntity);
  }

  @Override
  public void deleteCategory(Long id) throws NetflixException {
    if (!categoryRepository.existsById(id)) {
      throw new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC));
    }
    categoryRepository.deleteById(id);
  }
}