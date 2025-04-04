package com.nttdata.indhub.service.impl;

import com.nttdata.indhub.persistence.entity.ChapterEntity;
import com.nttdata.indhub.persistence.repository.ChapterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;
import com.nttdata.indhub.controller.model.rest.SeasonRest;
import com.nttdata.indhub.controller.model.rest.restSeason.PostSeasonRest;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.exception.NetflixNotFoundException;
import com.nttdata.indhub.exception.error.ErrorDto;
import com.nttdata.indhub.mapper.SeasonMapper;
import com.nttdata.indhub.persistence.entity.SeasonEntity;
import com.nttdata.indhub.persistence.repository.SeasonRepository;
import com.nttdata.indhub.service.SeasonService;
import com.nttdata.indhub.util.constant.ExceptionConstantsUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {

  private final SeasonRepository seasonRepository;

  private final ChapterRepository chapterRepository;

  private final SeasonMapper seasonMapper;


  @Override
  public Page<PostSeasonRest> getAllSeasons(Pageable pageable) throws NetflixException {
    Page<SeasonEntity> page = seasonRepository.findAll(pageable);
    return page.map(seasonMapper::mapToRestPost);
  }

  @Override
  public PostSeasonRest getSeasonById(Long id) throws NetflixException {
    SeasonEntity season = seasonRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));

    return seasonMapper.mapToRestPost(season);
  }

  @Override
  public PostSeasonRest createSeason(PostSeasonRest season) throws NetflixException {
    SeasonEntity seasonEntity = seasonMapper.mapToEntity(season);
    seasonRepository.save(seasonEntity);
    return season;
  }

  @Override
  public PostSeasonRest updateSeason(PostSeasonRest season, Long id) throws NetflixException {
    SeasonEntity seasonEntity = seasonRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    seasonEntity.setName(season.getName());
    seasonRepository.save(seasonEntity);
    return seasonMapper.mapToRestPost(seasonEntity);
  }

  @Override
  public void deleteSeason(Long id) throws NetflixException {
    if (!seasonRepository.existsById(id)) {
      throw new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC));
    }
    seasonRepository.deleteById(id);
  }

  @Override
  public SeasonRest addChapterToSeason(Long seasonId, Long chapterId) throws NetflixException {
    SeasonEntity season = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    ChapterEntity chapterEntity = chapterRepository.findById(chapterId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    season.getChapters().add(chapterEntity);
    seasonRepository.save(season);
    return seasonMapper.mapToRest(season);
  }

  @Override
  public SeasonRest deleteChapterOfSeason(Long seasonId, Long chapterId) throws NetflixException {
    SeasonEntity season = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    for (int i = 0; i < season.getChapters().size(); i++) {
      if (season.getChapters().get(i).getId() == chapterId) {
        season.getChapters().remove(i);
      }
    }
    seasonRepository.save(season);
    return seasonMapper.mapToRest(season);
  }
}