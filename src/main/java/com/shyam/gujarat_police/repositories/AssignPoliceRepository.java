package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.AssignPolice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignPoliceRepository extends PagingAndSortingRepository<AssignPolice, Long> {
}
