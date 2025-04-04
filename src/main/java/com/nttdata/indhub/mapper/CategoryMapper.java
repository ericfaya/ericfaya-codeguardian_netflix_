package com.nttdata.indhub.mapper;

import com.nttdata.indhub.controller.model.rest.CategoryRest;
import com.nttdata.indhub.controller.model.rest.restTVShow.CategoryRestTVShow;
import org.mapstruct.Mapper;
import com.nttdata.indhub.persistence.entity.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity mapToEntity(CategoryRest rest);

    CategoryRest mapToRest(CategoryEntity entity);

    CategoryEntity mapToEntity(CategoryRestTVShow rest);

    CategoryRestTVShow mapToRestPost(CategoryEntity entity);

}
