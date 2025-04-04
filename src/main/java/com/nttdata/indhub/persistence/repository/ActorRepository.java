package com.nttdata.indhub.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.nttdata.indhub.persistence.entity.ActorEntity;

@Repository
public interface ActorRepository extends PagingAndSortingRepository<ActorEntity, Long>, CrudRepository<ActorEntity, Long> {


}