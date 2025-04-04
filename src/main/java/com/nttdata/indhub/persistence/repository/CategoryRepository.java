package com.nttdata.indhub.persistence.repository;

import com.nttdata.indhub.persistence.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long>, CrudRepository<CategoryEntity, Long> {


}