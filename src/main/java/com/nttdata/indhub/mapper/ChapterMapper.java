package com.nttdata.indhub.mapper;

import com.nttdata.indhub.controller.model.rest.restChapter.PostChapterRest;
import org.mapstruct.Mapper;
import com.nttdata.indhub.controller.model.rest.ChapterRest;
import com.nttdata.indhub.persistence.entity.ChapterEntity;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    ChapterEntity mapToEntity(ChapterRest rest);

    ChapterRest mapToRest(ChapterEntity entity);

    ChapterEntity mapToEntity(PostChapterRest rest);

    PostChapterRest mapToRestPost(ChapterEntity entity);
}
