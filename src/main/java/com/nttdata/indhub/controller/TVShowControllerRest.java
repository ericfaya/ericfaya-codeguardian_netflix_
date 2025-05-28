package com.nttdata.indhub.controller;

import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.TVShowRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.controller.model.rest.restTVShow.PostTVShowRest;
import com.nttdata.indhub.exception.NetflixException;

public interface TVShowControllerRest {

  NetflixResponse<D4iPageRest<PostTVShowRest>> fetchAllTVShows(int page, int size, Pageable pageable) throws NetflixException;

  NetflixResponse<PostTVShowRest> getTVShowById(Long id) throws NetflixException;

  NetflixResponse<PostTVShowRest> createTVShow(PostTVShowRest tvShow) throws NetflixException;

  NetflixResponse<PostTVShowRest> updateTVShow(PostTVShowRest tvShow) throws NetflixException;

  NetflixResponse<Object> deleteTVShow(Long id) throws NetflixException;

  NetflixResponse<TVShowRest> addSeasonToTVShow(Long seasonId, Long tvShowId) throws NetflixException;

  NetflixResponse<TVShowRest> deleteSeasonOfTVShow(Long seasonId, Long tvShowId) throws NetflixException;

  NetflixResponse<TVShowRest> ToAddCategoryToTVShow(Long categoryId, Long tvShowId) throws NetflixException;

  NetflixResponse<TVShowRest> eleteCategoryOfTVShow(Long categoryId, Long tvShowId) throws NetflixException;
}
