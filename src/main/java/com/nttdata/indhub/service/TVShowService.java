package com.nttdata.indhub.service;

import com.nttdata.indhub.controller.model.rest.TVShowRest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.restTVShow.PostTVShowRest;
import com.nttdata.indhub.exception.NetflixException;

public interface TVShowService {

  Page<PostTVShowRest> getAllTVShows(Pageable pageable) throws NetflixException;

  PostTVShowRest getTVShowById(Long id) throws NetflixException;

  PostTVShowRest createTVShow(PostTVShowRest tvShow) throws NetflixException;

  PostTVShowRest updateTVShow(PostTVShowRest tvShow, Long id) throws NetflixException;

  void deleteTVShow(Long id) throws NetflixException;

  TVShowRest addSeasonToTVShow(Long seasonId, Long tvShowId) throws NetflixException;

  TVShowRest deleteSeasonOfTVShow(Long seasonId, Long tvShowId) throws NetflixException;

  TVShowRest addCategoryToTVShow(Long categoryId, Long tvShowId) throws NetflixException;

  TVShowRest deleteCategoryOfTVShow(Long categoryId, Long tvShowId) throws NetflixException;

}