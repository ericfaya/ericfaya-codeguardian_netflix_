package com.nttdata.indhub.persistence.repository;

import com.nttdata.indhub.persistence.entity.ChapterEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends PagingAndSortingRepository<ChapterEntity, Long>, CrudRepository<ChapterEntity, Long> {


}