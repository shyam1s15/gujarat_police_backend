package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.Designation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesignationRepository extends PagingAndSortingRepository<Designation, Long> {
    Optional<Designation> findByName(String designationName);
}
