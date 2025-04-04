package com.nttdata.indhub.controller;

import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.ChapterRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.controller.model.rest.restChapter.PostChapterRest;
import com.nttdata.indhub.exception.NetflixException;

public interface ChapterControllerRest {

  NetflixResponse<D4iPageRest<PostChapterRest>> getAllChapters(int page, int size, Pageable pageable) throws NetflixException;

  NetflixResponse<PostChapterRest> getChapterById(Long id) throws NetflixException;

  NetflixResponse<PostChapterRest> createChapter(PostChapterRest chapter) throws NetflixException;

  NetflixResponse<PostChapterRest> updateChapter(PostChapterRest chapter) throws NetflixException;

  NetflixResponse<Object> deleteChapter(Long id) throws NetflixException;

  NetflixResponse<ChapterRest> addActorToChapter(Long actorId, Long chapterId) throws NetflixException;

  NetflixResponse<ChapterRest> deleteActorOfChapter(Long actorId, Long chapterId) throws NetflixException;
}
