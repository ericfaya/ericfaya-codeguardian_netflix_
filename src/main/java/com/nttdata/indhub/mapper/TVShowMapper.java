package com.nttdata.indhub.mapper;

import com.nttdata.indhub.controller.model.rest.restTVShow.PostTVShowRest;
import org.mapstruct.Mapper;
import com.nttdata.indhub.controller.model.rest.TVShowRest;
import com.nttdata.indhub.persistence.entity.TVShowEntity;

@Mapper(componentModel = "spring")
public interface TVShowMapper {

    TVShowEntity mapToEntity(TVShowRest rest);

    TVShowRest mapToRest(TVShowEntity entity);

    TVShowEntity mapToEntity(PostTVShowRest rest);

    PostTVShowRest mapToRestPost(TVShowEntity entity);
}