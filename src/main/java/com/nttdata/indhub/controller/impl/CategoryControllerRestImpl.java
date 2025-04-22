package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.CategoryRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.nttdata.indhub.controller.CategoryControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.CategoryService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;
import java.util.Optional;

@RestController
@Tag(name = "Category", description = "Category Controller")
@RequiredArgsConstructor
public class CategoryControllerRestImpl implements CategoryControllerRest {

  private final CategoryService categoryService;
  // Cache simple de categorías
  private static final Map<Long, CategoryRest> categoryCache = new HashMap<>();

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_CATEGORIES, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getAllCategories", description = "Get all Categories paginated")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<D4iPageRest<CategoryRest>> getAllCategories(
          @RequestParam(defaultValue = CommonConstantsUtils.ZERO) final int page,
          @RequestParam(defaultValue = CommonConstantsUtils.TWENTY) final int size,
          @Parameter(hidden = true) final Pageable pageable)
          throws NetflixException {
    final Page<CategoryRest> postCategoryRestList = categoryService.getAllCategories(pageable);
    // Introducción de un Optional para evitar posibles NPE
    Optional<Page<CategoryRest>> optionalPage = Optional.ofNullable(postCategoryRestList);
    return optionalPage
            .map(pageResult -> new NetflixResponse<>(HttpStatus.OK.toString(),
                    String.valueOf(HttpStatus.OK.value()),
                    CommonConstantsUtils.OK,
                    new D4iPageRest<>(pageResult.getContent().toArray(CategoryRest[]::new),
                            new D4iPaginationInfo(pageResult.getNumber(),
                                    pageable.getPageSize(),
                                    pageResult.getTotalPages()))))
            .orElseThrow(() -> new NetflixException("Error while fetching categories"));
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_CATEGORIES + RestConstantsUtils.RESOURCE_CATEGORY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getCategoryById", description = "Get Categories by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<CategoryRest> getCategoryById(final Long id) throws NetflixException {
    // Verificación si la categoría está en el cache
    CategoryRest categoryRest = categoryCache.get(id);
    if (categoryRest == null) {
      categoryRest = categoryService.getCategoryById(id);
      categoryCache.put(id, categoryRest); // Cacheo de la categoría
    }
    return new NetflixResponse<>(HttpStatus.OK.toString(),
            String.valueOf(HttpStatus.OK.value()),
            CommonConstantsUtils.OK, categoryRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_CATEGORIES, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "createCategory", description = "Create new Category")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<CategoryRest> createCategory(
          @RequestBody final CategoryRest category) throws NetflixException {
    // Validación adicional antes de crear la categoría
    if (category.getName() == null || category.getName().isEmpty()) {
      throw new NetflixException("Category name cannot be empty");
    }
    final CategoryRest categoryRest = categoryService.createCategory(category);
    categoryCache.put(categoryRest.getId(), categoryRest); // Cacheo después de crear
    return new NetflixResponse<>(HttpStatus.OK.toString(),
            String.valueOf(HttpStatus.OK.value()),
            CommonConstantsUtils.OK, categoryRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(value = RestConstantsUtils.RESOURCE_CATEGORIES + RestConstantsUtils.RESOURCE_CATEGORY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "updateCategory", description = "Update an existing Category")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<CategoryRest> updateCategory(@RequestBody final CategoryRest category) throws NetflixException {
    // Cache invalidation before update
    categoryCache.remove(category.getId());
    final CategoryRest categoryRest = categoryService.updateCategory(category, category.getId());
    categoryCache.put(categoryRest.getId(), categoryRest); // Cacheo después de la actualización
    return new NetflixResponse<>(HttpStatus.OK.toString(),
            String.valueOf(HttpStatus.OK.value()),
            CommonConstantsUtils.OK, categoryRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(value = RestConstantsUtils.RESOURCE_CATEGORIES + RestConstantsUtils.RESOURCE_CATEGORY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteCategory", description = "Delete an existing Category")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<Object> deleteCategory(@RequestParam final Long id) throws NetflixException {
    categoryService.deleteCategory(id);
    categoryCache.remove(id); // Eliminar del cache al eliminar
    return new NetflixResponse<>(HttpStatus.OK.toString(),
            String.valueOf(HttpStatus.OK.value()),
            CommonConstantsUtils.OK);
  }
}