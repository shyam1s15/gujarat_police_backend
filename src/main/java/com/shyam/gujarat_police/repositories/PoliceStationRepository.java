package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.PoliceStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PoliceStationRepository extends PagingAndSortingRepository<PoliceStation, Long> {
    Optional<PoliceStation> findByPoliceStationName(String stationName);

    @Query("select station from PoliceStation station where station.policeStationName like ?1 or station.policeStationNameInGujarati like ?2")
    Optional<PoliceStation> findByNameInGujaratiOrEnglish(String nameInEnglish, String nameInGujarati);
}
