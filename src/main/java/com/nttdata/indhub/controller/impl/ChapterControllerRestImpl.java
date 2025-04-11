package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.ChapterRest;
import com.nttdata.indhub.controller.model.rest.restChapter.PostChapterRest;
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
import com.nttdata.indhub.controller.ChapterControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.ChapterService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "Chapter", description = "Chapter Controller")
@RequiredArgsConstructor
public class ChapterControllerRestImpl implements ChapterControllerRest {

  private final ChapterService chapterService;

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getAllChapters", description = "Get all Chapter paginated")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<D4iPageRest<PostChapterRest>> getAllChapters(
      @RequestParam(defaultValue = CommonConstantsUtils.ZERO) final int page,
      @RequestParam(defaultValue = CommonConstantsUtils.TWENTY) final int size,
      @Parameter(hidden = true) final Pageable pageable)
      throws NetflixException {
    final Page<PostChapterRest> postChapterRestList = chapterService.getAllChapters(pageable);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK,
        new D4iPageRest<>(postChapterRestList.getContent().toArray(PostChapterRest[]::new),
            new D4iPaginationInfo(postChapterRestList.getNumber(),
                pageable.getPageSize(),
                postChapterRestList.getTotalPages())));
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "getChapterById", description = "Get Chapters by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostChapterRest> getChapterById(final Long id) throws NetflixException {
    final PostChapterRest postChapterRest = chapterService.getChapterById(id);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, postChapterRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "createChapter", description = "Create new Chapter")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostChapterRest> createChapter(
      @RequestBody final PostChapterRest chapter) throws NetflixException {
    final PostChapterRest chapterRest = chapterService.createChapter(chapter);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, chapterRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "updateChapter", description = "Update an existing Chapter")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<PostChapterRest> updateChapter(@RequestBody final PostChapterRest chapter) throws NetflixException {
    final PostChapterRest chapterRest = chapterService.updateChapter(chapter, chapter.getId());
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, chapterRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteChapter", description = "Delete an existing Chapter")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<Object> deleteChapter(@RequestParam final Long id) throws NetflixException {
    chapterService.deleteChapter(id);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID
      + RestConstantsUtils.RESOURCE_ACTORS + RestConstantsUtils.RESOURCE_ACTOR_ID + RestConstantsUtils.ADD
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "addActorToChapter", description = "Add an actor to Chapter")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<ChapterRest> addActorToChapter(@RequestParam final Long actorId, @RequestParam final Long chapterId) throws NetflixException {
    final ChapterRest chapterRest = chapterService.addActorToChapter(actorId, chapterId);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, chapterRest);
  }

  @Override
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID
      + RestConstantsUtils.RESOURCE_ACTORS + RestConstantsUtils.RESOURCE_ACTOR_ID + RestConstantsUtils.DELETE
      , produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "deleteActorOfChapter", description = "Delete an Actor of Chapter")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
  })
  public NetflixResponse<ChapterRest> deleteActorOfChapter(@RequestParam final Long actorId, @RequestParam final Long chapterId) throws NetflixException {
    final ChapterRest chapterRest = chapterService.deleteActorOfChapter(actorId, chapterId);
    return new NetflixResponse<>(HttpStatus.OK.toString(),
        String.valueOf(HttpStatus.OK.value()),
        CommonConstantsUtils.OK, chapterRest);
  }
}
