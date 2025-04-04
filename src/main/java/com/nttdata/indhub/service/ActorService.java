package com.nttdata.indhub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.ActorRest;
import com.nttdata.indhub.controller.model.rest.restActor.PostActorRest;
import com.nttdata.indhub.exception.NetflixException;

public interface ActorService {

    Page<PostActorRest> getAllActors(Pageable pageable) throws NetflixException;

    PostActorRest getActorById(Long id) throws NetflixException;

    PostActorRest createActor(PostActorRest actor) throws NetflixException;

    PostActorRest updateActor(PostActorRest actor, Long id) throws NetflixException;

    void deleteActor(Long id) throws NetflixException;

    ActorRest getActorTVShowsChapters(Long id) throws NetflixException;
}