package com.nttdata.indhub.mapper;

import com.nttdata.indhub.controller.model.rest.restSeason.PostSeasonRest;
import org.mapstruct.Mapper;
import com.nttdata.indhub.controller.model.rest.SeasonRest;
import com.nttdata.indhub.persistence.entity.SeasonEntity;

@Mapper(componentModel = "spring")
public interface SeasonMapper {

    SeasonEntity mapToEntity(SeasonRest rest);

    SeasonRest mapToRest(SeasonEntity entity);

    SeasonEntity mapToEntity(PostSeasonRest rest);

    PostSeasonRest mapToRestPost(SeasonEntity entity);
}
