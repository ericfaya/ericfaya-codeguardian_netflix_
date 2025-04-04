package com.nttdata.indhub.service.impl;

import com.nttdata.indhub.persistence.entity.CategoryEntity;
import com.nttdata.indhub.persistence.entity.SeasonEntity;
import com.nttdata.indhub.persistence.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;
import com.nttdata.indhub.controller.model.rest.TVShowRest;
import com.nttdata.indhub.controller.model.rest.restTVShow.PostTVShowRest;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.exception.NetflixNotFoundException;
import com.nttdata.indhub.exception.error.ErrorDto;
import com.nttdata.indhub.mapper.TVShowMapper;
import com.nttdata.indhub.persistence.entity.TVShowEntity;
import com.nttdata.indhub.persistence.repository.SeasonRepository;
import com.nttdata.indhub.persistence.repository.TVShowRepository;
import com.nttdata.indhub.service.TVShowService;
import com.nttdata.indhub.util.constant.ExceptionConstantsUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class TVShowServiceImpl implements TVShowService {

  private final TVShowRepository tvShowRepository;

  private final SeasonRepository seasonRepository;

  private final CategoryRepository categoryRepository;

  private final TVShowMapper tvShowMapper;


  @Override
  public Page<PostTVShowRest> getAllTVShows(Pageable pageable) throws NetflixException {
    Page<TVShowEntity> page = tvShowRepository.findAll(pageable);
    return page.map(tvShowMapper::mapToRestPost);
  }

  @Override
  public PostTVShowRest getTVShowById(Long id) throws NetflixException {
    TVShowEntity tvShow = tvShowRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));

    return tvShowMapper.mapToRestPost(tvShow);
  }

  @Override
  public PostTVShowRest createTVShow(PostTVShowRest tvShow) throws NetflixException {
    TVShowEntity tvShowEntity = tvShowMapper.mapToEntity(tvShow);
    tvShowRepository.save(tvShowEntity);
    return tvShow;
  }

  @Override
  public PostTVShowRest updateTVShow(PostTVShowRest tvShow, Long id) throws NetflixException {
    TVShowEntity tvShowEntity = tvShowRepository.findById(id)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    tvShowEntity.setName(tvShow.getName());
    tvShowRepository.save(tvShowEntity);
    return tvShowMapper.mapToRestPost(tvShowEntity);
  }

  @Override
  public void deleteTVShow(Long id) throws NetflixException {
    if (!tvShowRepository.existsById(id)) {
      throw new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC));
    }
    tvShowRepository.deleteById(id);
  }

  @Override
  public TVShowRest addSeasonToTVShow(Long seasonId, Long tvShowId) throws NetflixException {
   TVShowEntity tvShow = tvShowRepository.findById(tvShowId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    SeasonEntity seasonEntity = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    tvShow.getSeasons().add(seasonEntity);
    seasonEntity.setTvShow(tvShow);
    tvShowRepository.save(tvShow);
    seasonRepository.save(seasonEntity);
    return tvShowMapper.mapToRest(tvShow);
  }

  @Override
  public TVShowRest deleteSeasonOfTVShow(Long seasonId, Long tvShowId) throws NetflixException {
    TVShowEntity tvShow = tvShowRepository.findById(tvShowId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    SeasonEntity seasonEntity = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    for (int i = 0; i < tvShow.getSeasons().size(); i++) {
      if (tvShow.getSeasons().get(i).getId() == seasonId) {
        tvShow.getSeasons().remove(i);
      }
    }
    seasonEntity.setTvShow(null);
    tvShowRepository.save(tvShow);
    seasonRepository.save(seasonEntity);
    return tvShowMapper.mapToRest(tvShow);
  }

  @Override
  public TVShowRest addCategoryToTVShow(Long categoryId, Long tvShowId) throws NetflixException {
    TVShowEntity tvShow = tvShowRepository.findById(tvShowId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    tvShow.getCategories().add(categoryEntity);
    tvShowRepository.save(tvShow);
    return tvShowMapper.mapToRest(tvShow);
  }

  @Override
  public TVShowRest deleteCategoryOfTVShow(Long categoryId, Long tvShowId) throws NetflixException {
    TVShowEntity tvShow = tvShowRepository.findById(tvShowId)
        .orElseThrow(() -> new NetflixNotFoundException(new ErrorDto(ExceptionConstantsUtils.NOT_FOUND_GENERIC)));
    for (int i = 0; i < tvShow.getCategories().size(); i++) {
      if (tvShow.getCategories().get(i).getId() == categoryId) {
        tvShow.getCategories().remove(i);
      }
    }
    tvShowRepository.save(tvShow);
    return tvShowMapper.mapToRest(tvShow);
  }
}