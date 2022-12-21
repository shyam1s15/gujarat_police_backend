package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.AssignPolice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AssignPoliceRepository extends PagingAndSortingRepository<AssignPolice, Long>, AssignPoliceBaseRepository {
//    @Modifying
//    @Transactional
//    @Query("Update OrderFeedback set retrieveStatus = ?2 where orderId = ?1 and orderType = 'trial'")
//    void updateFeedbackStatus(Long orderId, Integer status);

//    asp.police.id = :pId or
//    @Query("select count(asp) > 0 from AssignPolice asp where  asp.event.id = :eId and :dutyStartDate >= asp.event.eventStartDate ")
//    Boolean isPoliceAssignedV2(Long pId, Date dutyStartDate, Long eId);

    @Query("select count(asp) > 0 from AssignPolice asp where asp.police.id = :pId and (asp.event.id = :eId and :dutyStartDate >= asp.event.eventStartDate) ")
    boolean isPoliceAssignedV3(Long pId, Date dutyStartDate, Long eId);
}
