package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.PoliceStation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PoliceStationRepository extends PagingAndSortingRepository<PoliceStation, Long> {
    Optional<PoliceStation> findByPoliceStationName(String stationName);
}
