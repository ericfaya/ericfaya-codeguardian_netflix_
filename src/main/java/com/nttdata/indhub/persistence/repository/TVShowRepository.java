package com.nttdata.indhub.persistence.repository;

import com.nttdata.indhub.persistence.entity.TVShowEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowRepository extends PagingAndSortingRepository<TVShowEntity, Long>, CrudRepository<TVShowEntity, Long> {


}