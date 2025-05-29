package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.TVShowRest;
import com.nttdata.indhub.controller.model.rest.restTVShow.PostTVShowRest;
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
import com.nttdata.indhub.controller.TVShowControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.TVShowService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "TVShow", description = "TVShow Controller")
@RequiredArgsConstructor
public class TVShowControllerRestImpl implements TVShowControllerRest {

  private final TVShowService service;

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getAllTVShows", description = "Get all TVShow paginated")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<D4iPageRest<PostTVShowRest>> fetchAllTVShows(
          @RequestParam(defaultValue = CommonConstantsUtils.ZERO) final int page,
          @RequestParam(defaultValue = CommonConstantsUtils.TWENTY) final int size,
          @Parameter(hidden = true) final Pageable pageable)
          throws NetflixException {
    final Page<PostTVShowRest> postTVShowRestList = service.getAllTVShows(pageable);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
            String.valueOf(HttpStatus.OK.value()),
            CommonConstantsUtils.OK,
            new D4iPageRest<>(postTVShowRestList.getContent().toArray(PostTVShowRest[]::new),
                    new D4iPaginationInfo(postTVShowRestList.getNumber(),
                            pageable.getPageSize(),
                            postTVShowRestList.getTotalPages())));
  }


  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS + RestConstantsUtils.RESOURCE_TVSHOW_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getTVShowById", description = "Get TVShow by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostTVShowRest> getTVShowById(final Long id) throws NetflixException {
    final PostTVShowRest postTVShowRest = service.getTVShowById(id);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, postTVShowRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "createTVShow", description = "Create new TVShow")
  @ApiResponses(value = {

      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),

      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),

      @ApiResponse(responseCode = "200")

  })

  public NetflixResponse<PostTVShowRest> ethodToCeateTVShow(

      @RequestBody final PostTVShowRest tvShow) throws NetflixException {
    final PostTVShowRest tvShowRest = service.createTVShow(tvShow);

    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, tvShowRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS + RestConstantsUtils.RESOURCE_TVSHOW_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "updateTVShow", description = "Update an existing TVShow")
  @ApiResponses(value = {

      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),

          @ApiResponse(responseCode = "200"),

      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostTVShowRest> methodToUpdateTVShow(@RequestBody final PostTVShowRest tvShow) throws NetflixException {

    final PostTVShowRest tvShowRest = service.updateTVShow(tvShow, tvShow.getId());

    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, tvShowRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS + RestConstantsUtils.RESOURCE_TVSHOW_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteTVShow", description = "Delete an existing TVShow")
  @ApiResponses(value = {

      @ApiResponse(responseCode = "200"),

      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),

      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<Object> methodTodeleteTVShow(@RequestParam final Long id) throws NetflixException {
    service.deleteTVShow(id);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS + RestConstantsUtils.RESOURCE_TVSHOW_ID
      + RestConstantsUtils.RESOURCE_SEASONS + RestConstantsUtils.RESOURCE_SEASON_ID + RestConstantsUtils.ADD
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "addSeasonToTVShow", description = "Add a Season to TVShow")
  @ApiResponses(value = {

      @ApiResponse(responseCode = "200"),

      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),

      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

  })
  public NetflixResponse<TVShowRest> methodToaddSeasonToTVShow(@RequestParam final Long seasonId, @RequestParam final Long tvShowId) throws NetflixException {
    final TVShowRest tvShowRest = service.addSeasonToTVShow(seasonId, tvShowId);

    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, tvShowRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS + RestConstantsUtils.RESOURCE_TVSHOW_ID
      + RestConstantsUtils.RESOURCE_SEASONS + RestConstantsUtils.RESOURCE_SEASON_ID + RestConstantsUtils.DELETE
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteSeasonToTVShow", description = "Delete a Season of TVShow")
  @ApiResponses(value = {

      @ApiResponse(responseCode = "200"),

      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),

      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<TVShowRest> methodToDeleteSeasonOfTVShow(@RequestParam final Long seasonId, @RequestParam final Long tvShowId) throws NetflixException {
    final TVShowRest tvShowRest = service.deleteSeasonOfTVShow(seasonId, tvShowId);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, tvShowRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS + RestConstantsUtils.RESOURCE_TVSHOW_ID
      + RestConstantsUtils.RESOURCE_CATEGORIES + RestConstantsUtils.RESOURCE_CATEGORY_ID + RestConstantsUtils.ADD
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "addCategoryToTVShow", description = "Add a Category to TVShow")
  @ApiResponses(value = {

      @ApiResponse(responseCode = "200"),

      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),

      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

  })
  public NetflixResponse<TVShowRest> methodToAddCategoryToTVShow(@RequestParam final Long categoryId, @RequestParam final Long tvShowId) throws NetflixException {
    final TVShowRest tvShowRest = service.addCategoryToTVShow(categoryId, tvShowId);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, tvShowRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_TVSHOWS + RestConstantsUtils.RESOURCE_TVSHOW_ID
      + RestConstantsUtils.RESOURCE_CATEGORIES + RestConstantsUtils.RESOURCE_CATEGORY_ID + RestConstantsUtils.DELETE
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteCategoryToTVShow", description = "Delete a Category to TVShow")
  @ApiResponses(value = {

      @ApiResponse(responseCode = "200"),

      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),

      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })

  public NetflixResponse<TVShowRest> MethodToDeleteCategoryOfTVShow(@RequestParam final Long categoryId, @RequestParam final Long tvShowId) throws NetflixException {
    final TVShowRest tvShowRest = service.deleteCategoryOfTVShow(categoryId, tvShowId);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, tvShowRest);
  }
}
