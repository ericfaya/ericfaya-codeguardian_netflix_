package com.nttdata.indhub.service.impl;


import com.nttdata.indhub.controller.model.rest.ActorRest;
import com.nttdata.indhub.controller.model.rest.SeasonRest;
import com.nttdata.indhub.controller.model.rest.TVShowRest;
import com.nttdata.indhub.controller.model.rest.restActor.ChapterRestActor;
import com.nttdata.indhub.controller.model.rest.restActor.PostActorRest;
import com.nttdata.indhub.controller.model.rest.restActor.TVShowRestActor;
import com.nttdata.indhub.persistence.entity.ActorEntity;
import com.nttdata.indhub.persistence.entity.ChapterEntity;
import com.nttdata.indhub.persistence.entity.SeasonEntity;
import com.nttdata.indhub.persistence.entity.TVShowEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.mapper.ActorMapper;
import com.nttdata.indhub.mapper.ChapterMapper;
import com.nttdata.indhub.mapper.TVShowMapper;
import com.nttdata.indhub.persistence.repository.ActorRepository;
import com.nttdata.indhub.persistence.repository.ChapterRepository;
import com.nttdata.indhub.persistence.repository.TVShowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

  private static final ActorEntity ACTOR_ENTITY = new ActorEntity();
  private static final ActorEntity ACTOR_ENTITY2 = new ActorEntity();
  private static final ActorRest ACTOR_REST = new ActorRest();
  private static final PostActorRest POST_ACTOR_REST = new PostActorRest();
  private static final List<ChapterEntity> CHAPTER_ENTITY_LIST = new ArrayList<>();
  private static final ChapterEntity CHAPTER_ENTITY = new ChapterEntity();
  private static final List<ChapterRestActor> CHAPTER_REST_ACTOR_LIST = new ArrayList<>();
  private static final ChapterRestActor CHAPTER_REST_ACTOR = new ChapterRestActor();
  private static final SeasonEntity SEASON_ENTITY = new SeasonEntity();
  private static final SeasonRest SEASON_REST = new SeasonRest();
  private static final List<TVShowEntity> TV_SHOW_ENTITY_LIST = new ArrayList<>();
  private static final TVShowEntity TV_SHOW_ENTITY = new TVShowEntity();
  private static final List<TVShowRest> TV_SHOW_REST_LIST = new ArrayList<>();
  private static final List<TVShowRestActor> TV_SHOW_REST_ACTOR_LIST = new ArrayList<>();
  private static final TVShowRest TV_SHOW_REST = new TVShowRest();
  private static final List<ActorEntity> ACTOR_ENTITY_LIST = new ArrayList<>();
  private static final List<PostActorRest> POST_ACTOR_REST_LIST = new ArrayList<>();
  private static Page<ActorEntity> ACTOR_ENTITY_PAGE;
  private static Page<PostActorRest> ACTOR_REST_PAGE;

  @Mock
  public ActorRepository actorRepository;

  @Mock
  public ActorMapper actorMapper;

  @InjectMocks
  public ActorServiceImpl actorService;

  @BeforeEach
  public void setup() {
    CHAPTER_REST_ACTOR_LIST.clear();

    ACTOR_ENTITY.setId(1L);
    ACTOR_ENTITY.setName("TestingActor");
    ACTOR_ENTITY.setDescription("Description");

    ACTOR_ENTITY2.setId(2L);
    ACTOR_ENTITY2.setName("TestingActor2");
    ACTOR_ENTITY2.setDescription("Description2");

    ACTOR_REST.setId(1L);
    ACTOR_REST.setName("TestingActor");
    ACTOR_REST.setDescription("Description");

    POST_ACTOR_REST.setId(1L);
    POST_ACTOR_REST.setName("TestingActor");
    POST_ACTOR_REST.setDescription("Description");

    CHAPTER_ENTITY.setId(1L);
    CHAPTER_ENTITY.setName("TestingChapter");
    CHAPTER_ENTITY.setNumber(1);
    CHAPTER_ENTITY.setDuration(10.0);


    CHAPTER_REST_ACTOR.setId(CHAPTER_ENTITY.getId());
    CHAPTER_REST_ACTOR.setName(CHAPTER_ENTITY.getName());
    CHAPTER_REST_ACTOR.setNumber(CHAPTER_ENTITY.getNumber());
    CHAPTER_REST_ACTOR.setDuration(CHAPTER_ENTITY.getDuration());

    SEASON_ENTITY.setId(1L);
    SEASON_ENTITY.setName("TestingSeason");
    SEASON_ENTITY.setNumber(1);

    CHAPTER_ENTITY.setSeason(SEASON_ENTITY);

    SEASON_REST.setId(1L);
    SEASON_REST.setName("TestingSeason");
    SEASON_REST.setNumber(1);

    TV_SHOW_ENTITY.setId(1L);
    TV_SHOW_ENTITY.setName("TestingTVShow");
    TV_SHOW_ENTITY.setShortDescription("ShortDescriptionTest");
    TV_SHOW_ENTITY.setLongDescription("LongDescriptionTest");
    TV_SHOW_ENTITY.setYear(2000);
    TV_SHOW_ENTITY.setRecommendedAge(18);
    TV_SHOW_ENTITY.setAdvertising("AdvertisingTest");

    SEASON_ENTITY.setTvShow(TV_SHOW_ENTITY);


    TV_SHOW_REST.setId(TV_SHOW_ENTITY.getId());
    TV_SHOW_REST.setName(TV_SHOW_ENTITY.getName());
    TV_SHOW_REST.setShortDescription(TV_SHOW_ENTITY.getShortDescription());
    TV_SHOW_REST.setLongDescription(TV_SHOW_ENTITY.getLongDescription());
    TV_SHOW_REST.setYear(TV_SHOW_ENTITY.getYear());
    TV_SHOW_REST.setRecommendedAge(TV_SHOW_ENTITY.getRecommendedAge());
    TV_SHOW_REST.setAdvertising(TV_SHOW_ENTITY.getAdvertising());

    SEASON_REST.setTvShow(TV_SHOW_REST);

    CHAPTER_ENTITY_LIST.add(CHAPTER_ENTITY);
    CHAPTER_REST_ACTOR_LIST.add(CHAPTER_REST_ACTOR);

    TV_SHOW_ENTITY_LIST.clear();
    TV_SHOW_ENTITY_LIST.add(TV_SHOW_ENTITY);
    TV_SHOW_REST_LIST.clear();
    TV_SHOW_REST_LIST.add(TV_SHOW_REST);
    TV_SHOW_REST_ACTOR_LIST.clear();
    TV_SHOW_REST_ACTOR_LIST.add(new TVShowRestActor(1L, "TestingTVShow", "ShortDescriptionTest", "LongDescriptionTest", 2000, 18, "AdvertisingTest"));

    ACTOR_ENTITY.setChapters(CHAPTER_ENTITY_LIST);
    ACTOR_REST.setChapters(CHAPTER_REST_ACTOR_LIST);
    ACTOR_ENTITY.setTvShows(TV_SHOW_ENTITY_LIST);
    ACTOR_REST.setTvShows(TV_SHOW_REST_ACTOR_LIST);

    ACTOR_ENTITY_LIST.clear();
    ACTOR_ENTITY_LIST.add(ACTOR_ENTITY);
    ACTOR_ENTITY_LIST.add(ACTOR_ENTITY2);
    POST_ACTOR_REST_LIST.add(POST_ACTOR_REST);

    ACTOR_ENTITY_PAGE = new PageImpl<>(ACTOR_ENTITY_LIST);
    ACTOR_REST_PAGE = new PageImpl<>(POST_ACTOR_REST_LIST);
  }

  @Test
  void getAllActors() throws NetflixException {
    Pageable pageable = Pageable.unpaged();
    when(actorRepository.findAll(pageable)).thenReturn(ACTOR_ENTITY_PAGE);
    Page<PostActorRest> actorRests = actorService.getAllActors(pageable);
    assertEquals(2, actorRests.getContent().size());
  }

  @Test
  void getActorByIdException() {
    when(actorRepository.findById(10L)).thenReturn(Optional.empty());
    assertThrows(NetflixException.class, () -> {
      actorService.getActorById(10L);    });
  }

  @Test
  void updateActor() throws NetflixException {
    PostActorRest postActorRest = POST_ACTOR_REST;
    postActorRest.setName("UpdateTest");
    when(actorRepository.findById(1L)).thenReturn(Optional.of(ACTOR_ENTITY));
    when(actorMapper.mapToRestPost(ACTOR_ENTITY)).thenReturn(postActorRest);
    PostActorRest update = actorService.updateActor(postActorRest, postActorRest.getId());
    assertEquals(POST_ACTOR_REST.getName(), update.getName());
  }

  @Test
  void updateActorException() {
    Long id = 11L;
    PostActorRest actorRest = new PostActorRest(id, "TestingActor", "Description");
    assertThrows(NetflixException.class, () -> {
      actorService.updateActor(actorRest, id);    });
  }

  @Test
  void deleteActorException() {
    Long id = 12L;
    assertThrows(NetflixException.class, () -> {
      actorService.deleteActor(id);    });
  }

  @Test
  void getActorTVShowsChapters() throws NetflixException {
    when(actorRepository.findById(1L)).thenReturn(Optional.of(ACTOR_ENTITY));
    when(actorMapper.mapToRest(ACTOR_ENTITY)).thenReturn(ACTOR_REST);
    ActorRest actorRest = actorService.getActorTVShowsChapters(1L);
    assertEquals(1, actorRest.getChapters().size());
    assertEquals(1, actorRest.getTvShows().size());
  }

  @Test
  void getActorTVShowsChaptersNotFoundException() {
    assertThrows(NetflixException.class, () -> {
      actorService.getActorTVShowsChapters(14L);
    });
  }

  @Test
  void getActorTVShowsChaptersEmptyChaptersException() {
    ActorEntity actorEntity = ACTOR_ENTITY;
    List<ChapterEntity> chapterEntityList = new ArrayList<>();
    actorEntity.setChapters(chapterEntityList);
    when(actorRepository.findById(1L)).thenReturn(Optional.of(actorEntity));
    assertThrows(NetflixException.class, () -> {
      actorService.getActorTVShowsChapters(1L);
    });
  }

  @Test
  void getActorTVShowsChaptersEmptyTVShowsException() {
    ActorEntity actorEntity = ACTOR_ENTITY;
    List<TVShowEntity> tvShowEntityList = new ArrayList<>();
    actorEntity.setTvShows(tvShowEntityList);
    when(actorRepository.findById(1L)).thenReturn(Optional.of(actorEntity));
    assertThrows(NetflixException.class, () -> {
      actorService.getActorTVShowsChapters(1L);
    });
  }
}

