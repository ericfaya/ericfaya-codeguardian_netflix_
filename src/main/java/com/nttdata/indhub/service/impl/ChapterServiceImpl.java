package com.nttdata.indhub.service.impl;

import com.nttdata.indhub.persistence.entity.ActorEntity;
import com.nttdata.indhub.persistence.repository.ActorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;
import com.nttdata.indhub.controller.model.rest.ChapterRest;
import com.nttdata.indhub.controller.model.rest.restChapter.PostChapterRest;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.exception.NetflixNotFoundException;
import com.nttdata.indhub.exception.error.ErrorDto;
import com.nttdata.indhub.mapper.ChapterMapper;
import com.nttdata.indhub.persistence.entity.ChapterEntity;
import com.nttdata.indhub.persistence.repository.ChapterRepository;
import com.nttdata.indhub.service.ChapterService;
import com.nttdata.indhub.util.constant.ExceptionConstantsUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

  private final ChapterRepository chapterRepository;

  private final ActorRepository actorRepository;

  private final ChapterMapper chapterMapper;


  @Override
  public Page<PostChapterRest> getAllChapters(Pageable pageable) throws NetflixException {
    Page<ChapterEntity> page = chapterRepository.findAll(pageable);
    return page.map(chapterMapper::mapToRestPost);
  }

  @Override
  public PostChapterRest getChapterById(Long id) throws NetflixException {
    ChapterEntity chapter = chapterRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));

    return chapterMapper.mapToRestPost(chapter);
  }

  @Override
  public PostChapterRest createChapter(PostChapterRest chapter) throws NetflixException {
    ChapterEntity chapterEntity = chapterMapper.mapToEntity(chapter);
    chapterRepository.save(chapterEntity);
    return chapter;
  }

  @Override
  public PostChapterRest updateChapter(PostChapterRest chapter, Long id) throws NetflixException {
    ChapterEntity chapterEntity = chapterRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    chapterEntity.setName(chapter.getName());
    chapterRepository.save(chapterEntity);
    return chapterMapper.mapToRestPost(chapterEntity);
  }

  @Override
  public void deleteChapter(Long id) throws NetflixException {
    if (!chapterRepository.existsById(id)) {
      throw new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC));
    }
    chapterRepository.deleteById(id);
  }

  @Override
  public ChapterRest addActorToChapter(Long actorId, Long chapterId) throws NetflixException {
    ChapterEntity chapter = chapterRepository.findById(chapterId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    ActorEntity actorEntity = actorRepository.findById(actorId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    chapter.getActors().add(actorEntity);
    actorEntity.getChapters().add(chapter);
    chapterRepository.save(chapter);
    actorRepository.save(actorEntity);
    return chapterMapper.mapToRest(chapter);
  }

  @Override
  public ChapterRest deleteActorOfChapter(Long actorId, Long chapterId) throws NetflixException {
    ChapterEntity chapter = chapterRepository.findById(chapterId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    ActorEntity actorEntity = actorRepository.findById(actorId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    for (int i = 0; i < chapter.getActors().size(); i++) {
      if (chapter.getActors().get(i).getId() == actorId) {
        chapter.getActors().remove(i);
      }
    }
    actorEntity.setChapters(null);
    chapterRepository.save(chapter);
    actorRepository.save(actorEntity);
    return chapterMapper.mapToRest(chapter);
  }
}