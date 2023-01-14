package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.EventPoliceCount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPoliceCountRepository extends PagingAndSortingRepository<EventPoliceCount, Long>,  EventPoliceCountBaseRepository{
}
