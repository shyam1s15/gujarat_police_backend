package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
}
