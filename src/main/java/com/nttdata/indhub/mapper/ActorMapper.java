package com.nttdata.indhub.mapper;

import org.mapstruct.Mapper;
import com.nttdata.indhub.controller.model.rest.ActorRest;
import com.nttdata.indhub.controller.model.rest.restActor.PostActorRest;
import com.nttdata.indhub.persistence.entity.ActorEntity;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorEntity mapToEntity(ActorRest rest);

    ActorRest mapToRest(ActorEntity entity);

    ActorEntity mapToEntity(PostActorRest rest);

    PostActorRest mapToRestPost(ActorEntity entity);

}
