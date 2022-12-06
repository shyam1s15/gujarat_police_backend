package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.Police;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PoliceRepository extends PagingAndSortingRepository<Police, Long>, PoliceBaseRepository     {
    Optional<Police> findByBuckleNumber(String buckleNumber);
}
