package com.nttdata.indhub.persistence.repository;

import com.nttdata.indhub.persistence.entity.SeasonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends PagingAndSortingRepository<SeasonEntity, Long>, CrudRepository<SeasonEntity, Long> {


}