package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.SeasonRest;
import com.nttdata.indhub.controller.model.rest.restSeason.PostSeasonRest;
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
import com.nttdata.indhub.controller.SeasonControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.SeasonService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "Season", description = "Season Controller")
@RequiredArgsConstructor
public class SeasonControllerRestImpl implements SeasonControllerRest {

  private final SeasonService seasonService;

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_SEASONS, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getAllSeasons", description = "Get all Season paginated")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<D4iPageRest<PostSeasonRest>> getAllSeasons(
      @RequestParam(defaultValue = CommonConstantsUtils.ZERO) final int page,
      @RequestParam(defaultValue = CommonConstantsUtils.TWENTY) final int size,
      @Parameter(hidden = true) final Pageable pageable)
      throws NetflixException {
    final Page<PostSeasonRest> postSeasonRestList = seasonService.getAllSeasons(pageable);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK,
        new D4iPageRest<>(postSeasonRestList.getContent().toArray(PostSeasonRest[]::new),
            new D4iPaginationInfo(postSeasonRestList.getNumber(),
                pageable.getPageSize(),
                postSeasonRestList.getTotalPages())));
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_SEASONS + RestConstantsUtils.RESOURCE_SEASON_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getSeasonById", description = "Get Season by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostSeasonRest> getSeasonById(final Long id) throws NetflixException {
    final PostSeasonRest postSeasonRest = seasonService.getSeasonById(id);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, postSeasonRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_SEASONS, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "createSeason", description = "Create new Season")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostSeasonRest> createSeason(
      @RequestBody final PostSeasonRest season) throws NetflixException {
    final PostSeasonRest seasonRest = seasonService.createSeason(season);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, seasonRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(value = RestConstantsUtils.RESOURCE_SEASONS + RestConstantsUtils.RESOURCE_SEASON_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "updateSeason", description = "Update an existing Season")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostSeasonRest> updateSeason(@RequestBody final PostSeasonRest season) throws NetflixException {
    final PostSeasonRest seasonRest = seasonService.updateSeason(season, season.getId());
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, seasonRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(value = RestConstantsUtils.RESOURCE_SEASONS + RestConstantsUtils.RESOURCE_SEASON_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteSeason", description = "Delete an existing Season")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<Object> deleteSeason(@RequestParam final Long id) throws NetflixException {
    seasonService.deleteSeason(id);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_SEASONS + RestConstantsUtils.RESOURCE_SEASON_ID
      + RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID + RestConstantsUtils.ADD
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "addChapterToSeason", description = "Add a Chapter to Season")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<SeasonRest> addChapterToSeason(@RequestParam final Long seasonId, @RequestParam final Long chapterId) throws NetflixException {
    final SeasonRest seasonRest = seasonService.addChapterToSeason(seasonId, chapterId);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, seasonRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_SEASONS + RestConstantsUtils.RESOURCE_SEASON_ID
      + RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID + RestConstantsUtils.DELETE
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteChapterToSeason", description = "Delete a Chapter of Season")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<SeasonRest> deleteChapterOfSeason(@RequestParam final Long seasonId, @RequestParam final Long chapterId) throws NetflixException {
    final SeasonRest seasonRest = seasonService.deleteChapterOfSeason(seasonId, chapterId);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, seasonRest);
  }
}
