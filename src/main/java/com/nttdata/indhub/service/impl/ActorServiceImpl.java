package com.nttdata.indhub.service.impl;

import com.nttdata.indhub.persistence.entity.ChapterEntity;
import com.nttdata.indhub.persistence.entity.TVShowEntity;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;
import com.nttdata.indhub.controller.model.rest.ActorRest;
import com.nttdata.indhub.controller.model.rest.restActor.PostActorRest;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.exception.NetflixNotFoundException;
import com.nttdata.indhub.exception.error.ErrorDto;
import com.nttdata.indhub.mapper.ActorMapper;
import com.nttdata.indhub.persistence.entity.ActorEntity;
import com.nttdata.indhub.persistence.repository.ActorRepository;
import com.nttdata.indhub.service.ActorService;
import com.nttdata.indhub.util.constant.ExceptionConstantsUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    private final ActorMapper actorMapper;


    @Override
    public Page<PostActorRest> getAllActors(Pageable pageable) throws NetflixException {
        Page<ActorEntity> page = actorRepository.findAll(pageable);
        return page.map(actorMapper::mapToRestPost);
    }

    @Override
    public PostActorRest getActorById(Long id) throws NetflixException {
        ActorEntity actor = actorRepository.findById(id)
                .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));

        return actorMapper.mapToRestPost(actor);
    }

    public void netflixBadRequestChapterEmpty(ActorEntity actor) throws NetflixException {
        if (actor.getChapters().isEmpty()) {
            throw new NetflixException(ExceptionConstantsUtils.BAD_REQUEST_INT, "Chapters list is empty", new ErrorDto(ExceptionConstantsUtils.BAD_REQUEST_GENERIC));
        }
    }

    public void netflixBadRequestTVShowEmpty(ActorEntity actor) throws NetflixException {
        if (actor.getTvShows().isEmpty()) {
            throw new NetflixException(ExceptionConstantsUtils.BAD_REQUEST_INT, "TVShows list is empty", new ErrorDto(ExceptionConstantsUtils.BAD_REQUEST_GENERIC));
        }
    }

    @Override
    public PostActorRest createActor(PostActorRest actor) throws NetflixException {
        ActorEntity actorEntity = actorMapper.mapToEntity(actor);
        try {
            actorRepository.save(actorEntity);
        return actor;
        } catch (Exception e) {
            throw new NetflixException(ExceptionConstantsUtils.INTERNAL_SERVER_GENERIC_INT, "brutal error", new ErrorDto(ExceptionConstantsUtils.INTERNAL_SERVER_GENERIC));
        }
    }

    @Override
    public PostActorRest updateActor(PostActorRest actor, Long id) throws NetflixException {
        ActorEntity actorEntity = actorRepository.findById(id)
                .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
        actorEntity.setName(actor.getName());
        actorEntity.setDescription(actor.getDescription());

        try {
            actorRepository.save(actorEntity);
            return actorMapper.mapToRestPost(actorEntity);
        } catch (Exception e) {
            throw new NetflixException(ExceptionConstantsUtils.INTERNAL_SERVER_GENERIC_INT, "brutal error", new ErrorDto(ExceptionConstantsUtils.INTERNAL_SERVER_GENERIC));
        }
    }

    @Override
    public void deleteActor(Long id) throws NetflixException {
        if (!actorRepository.existsById(id)) {
            throw new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC));
        }
        actorRepository.deleteById(id);
    }

    @Override
    public ActorRest getActorTVShowsChapters(Long id) throws NetflixException {
        ActorEntity actor = actorRepository.findById(id)
                .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
        netflixBadRequestChapterEmpty(actor);
        netflixBadRequestTVShowEmpty(actor);
        Set<TVShowEntity> tvShowsSet = new HashSet<>();
        List<ChapterEntity> chapters = actor.getChapters();

        for (ChapterEntity chapter : chapters) {
            TVShowEntity tvShow = chapter.getSeason().getTvShow();
            tvShowsSet.add(tvShow);
        }

        actor.setTvShows(tvShowsSet.stream().toList());

        return actorMapper.mapToRest(actor);
    }

}