package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.ChapterRest;
import com.nttdata.indhub.controller.model.rest.restChapter.PostChapterRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    private final ChapterService service;

    private <T> NetflixResponse<T> buildResponse(T body) {
        return new NetflixResponse<>(HttpStatus.OK.toString(),
                String.valueOf(HttpStatus.OK.value()),
                CommonConstantsUtils.OK,
                body);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all Chapters", description = "Retrieve paginated list of chapters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostChapterRest.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)

    })
    public NetflixResponse<D4iPageRest<PostChapterRest>> getAllChapters(
            @RequestParam(defaultValue = CommonConstantsUtils.ZERO) final int page,
            @RequestParam(defaultValue = CommonConstantsUtils.TWENTY) final int size,
            @Parameter(hidden = true) final Pageable pageable)
            throws NetflixException {

        final Page<PostChapterRest> postChapterRestList = service.getAllChapters(pageable);
        return buildResponse(new D4iPageRest<>(postChapterRestList.getContent().toArray(PostChapterRest[]::new),
                new D4iPaginationInfo(postChapterRestList.getNumber(),
                        pageable.getPageSize(),
                        postChapterRestList.getTotalPages())));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve a chapter by ID", description = "Find a chapter by its ID")
    public NetflixResponse<PostChapterRest> getChapterById(@RequestParam final Long id) throws NetflixException {
        final PostChapterRest postChapterRest = service.getChapterById(id);
        return buildResponse(postChapterRest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create chapter", description = "Creates a new Chapter")
    public NetflixResponse<PostChapterRest> createChapter(
            @RequestBody final PostChapterRest chapter) throws NetflixException {
        final PostChapterRest chapterRest = service.createChapter(chapter);
        return buildResponse(chapterRest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modify chapter", description = "Updates an existing Chapter")
    public NetflixResponse<PostChapterRest> updateChapter(@RequestBody final PostChapterRest chapter) throws NetflixException {
        // Error intencionado: no se valida si getId() es null
        final PostChapterRest chapterRest = service.updateChapter(chapter, chapter.getId());
        return buildResponse(chapterRest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Remove chapter", description = "Deletes an existing Chapter")
    public NetflixResponse<Object> deleteChapter(@RequestParam final Long id) throws NetflixException {
        // Error intencionado: debería usarse @PathVariable
        service.deleteChapter(id);
        return buildResponse(null);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID
            + RestConstantsUtils.RESOURCE_ACTORS + RestConstantsUtils.RESOURCE_ACTOR_ID + RestConstantsUtils.ADD,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Attach actor", description = "Adds an actor to a chapter")
    public NetflixResponse<ChapterRest> addActorToChapter(@RequestParam final Long actorId, @RequestParam final Long chapterId) throws NetflixException {
        // Error intencionado: estos IDs deberían estar en el path, no como parámetros
        final ChapterRest chapterRest = service.addActorToChapter(actorId, chapterId);
        return buildResponse(chapterRest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = RestConstantsUtils.RESOURCE_CHAPTERS + RestConstantsUtils.RESOURCE_CHAPTER_ID
            + RestConstantsUtils.RESOURCE_ACTORS + RestConstantsUtils.RESOURCE_ACTOR_ID + RestConstantsUtils.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Detach actor", description = "Removes an actor from a chapter")
    public NetflixResponse<ChapterRest> deleteActorOfChapter(@RequestParam final Long actorId, @RequestParam final Long chapterId) throws NetflixException {
        final ChapterRest chapterRest = service.deleteActorOfChapter(actorId, chapterId);
        return buildResponse(chapterRest);
    }
}