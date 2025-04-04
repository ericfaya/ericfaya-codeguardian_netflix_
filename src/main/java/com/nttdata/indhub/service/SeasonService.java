package com.nttdata.indhub.service;

import com.nttdata.indhub.controller.model.rest.SeasonRest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nttdata.indhub.controller.model.rest.restSeason.PostSeasonRest;
import com.nttdata.indhub.exception.NetflixException;

public interface SeasonService {

  Page<PostSeasonRest> getAllSeasons(Pageable pageable) throws NetflixException;

  PostSeasonRest getSeasonById(Long id) throws NetflixException;

  PostSeasonRest createSeason(PostSeasonRest season) throws NetflixException;

  PostSeasonRest updateSeason(PostSeasonRest season, Long id) throws NetflixException;

  void deleteSeason(Long id) throws NetflixException;

  SeasonRest addChapterToSeason(Long seasonId, Long chapterId) throws NetflixException;

  SeasonRest deleteChapterOfSeason(Long seasonId, Long chapterId) throws NetflixException;

}