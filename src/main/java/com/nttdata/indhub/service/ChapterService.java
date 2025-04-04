package com.nttdata.indhub.service;

import com.nttdata.indhub.controller.model.rest.ChapterRest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.restChapter.PostChapterRest;
import com.nttdata.indhub.exception.NetflixException;

public interface ChapterService {

  Page<PostChapterRest> getAllChapters(Pageable pageable) throws NetflixException;

  PostChapterRest getChapterById(Long id) throws NetflixException;

  PostChapterRest createChapter(PostChapterRest chapter) throws NetflixException;

  PostChapterRest updateChapter(PostChapterRest chapter, Long id) throws NetflixException;

  void deleteChapter(Long id) throws NetflixException;

  ChapterRest addActorToChapter(Long actorId, Long chapterId) throws NetflixException;

  ChapterRest deleteActorOfChapter(Long actorId, Long chapterId) throws NetflixException;
}