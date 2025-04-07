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

@RestController
@Tag(name = "Category", description = "Category Controller")
@RequiredArgsConstructor
public class CategoryControllerRestImpl implements CategoryControllerRest {

  private final CategoryService categoryService;

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
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK,
        new D4iPageRest<>(postCategoryRestList.getContent().toArray(CategoryRest[]::new),
            new D4iPaginationInfo(postCategoryRestList.getNumber(),
                pageable.getPageSize(),
                postCategoryRestList.getTotalPages())));
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
    final CategoryRest postCategoryRest = categoryService.getCategoryById(id);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, postCategoryRest);
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
    final CategoryRest categoryRest = categoryService.createCategory(category);
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
    final CategoryRest categoryRest = categoryService.updateCategory(category, category.getId());
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
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK);
  }
}
