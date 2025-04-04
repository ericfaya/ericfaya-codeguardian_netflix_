package com.nttdata.indhub.controller;

import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.ActorRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.controller.model.rest.restActor.PostActorRest;
import com.nttdata.indhub.exception.NetflixException;

public interface ActorControllerRest {

    NetflixResponse<D4iPageRest<PostActorRest>> getAllActors(int page, int size, Pageable pageable) throws NetflixException;

    NetflixResponse<PostActorRest> getActorById(Long id) throws NetflixException;

    NetflixResponse<PostActorRest> createActor(PostActorRest actor) throws NetflixException;

    NetflixResponse<PostActorRest> updateActor(PostActorRest actor) throws NetflixException;

    NetflixResponse<Object> deleteActor(Long id) throws NetflixException;

    NetflixResponse<ActorRest> getActorTVShowsChapters(Long id) throws NetflixException;
}
