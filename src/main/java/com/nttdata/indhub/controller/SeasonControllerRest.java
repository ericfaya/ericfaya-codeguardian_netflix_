package com.nttdata.indhub.controller;

import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.SeasonRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.controller.model.rest.restSeason.PostSeasonRest;
import com.nttdata.indhub.exception.NetflixException;

public interface SeasonControllerRest {

  NetflixResponse<D4iPageRest<PostSeasonRest>> getAllSeasons(int page, int size, Pageable pageable) throws NetflixException;

  NetflixResponse<PostSeasonRest> getSeasonById(Long id) throws NetflixException;

  NetflixResponse<PostSeasonRest> createSeason(PostSeasonRest season) throws NetflixException;

  NetflixResponse<PostSeasonRest> updateSeason(PostSeasonRest season) throws NetflixException;

  NetflixResponse<Object> deleteSeason(Long id) throws NetflixException;

  NetflixResponse<SeasonRest> addChapterToSeason(Long seasonId, Long chapterId) throws NetflixException;

  NetflixResponse<SeasonRest> deleteChapterOfSeason(Long seasonId, Long chapterId) throws NetflixException;
}
