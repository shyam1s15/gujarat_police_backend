package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long>, ZoneBaseRepository {
}
