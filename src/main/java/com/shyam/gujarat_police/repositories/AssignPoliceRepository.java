package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.AssignPolice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AssignPoliceRepository extends PagingAndSortingRepository<AssignPolice, Long>, AssignPoliceBaseRepository {

    @Query("select count(asp) > 0 from AssignPolice asp where asp.police.id = :pId and (asp.event.id = :eId and :dutyStartDate >= asp.event.eventStartDate) ")
    boolean isPoliceAssignedForSpecificEvent(Long pId, Date dutyStartDate, Long eId);

    @Query("select count(asp) from AssignPolice asp where asp.police.id = :pId and (asp.event.id IN (:eventIds) and :dutyStartDate >= asp.event.eventStartDate) ")
//    @Query("select count(asp) from AssignPolice asp where asp.event.id IN (:eventIds) and :pId=:pId and :dutyStartDate = :dutyStartDate")
    int isPoliceAssignedForSpecificEvents(Long pId, Date dutyStartDate, List<Long> eventIds);

    // at max it can only give one police assign details
//    @Query("select count(asp) > 0 from AssignPolice asp where asp.police.id = :pId and :dutyStartDate >= asp.event.eventEndDate and event.startDate >= :currentDate")
//    boolean isPoliceAssignedForAanyEvents(Long pId, Date dutyStartDate, Date currentDate);
}
