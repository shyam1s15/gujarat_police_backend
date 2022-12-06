package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.AssignPolice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignPoliceRepository extends PagingAndSortingRepository<AssignPolice, Long>, AssignPoliceBaseRepository {
//    @Modifying
//    @Transactional
//    @Query("Update OrderFeedback set retrieveStatus = ?2 where orderId = ?1 and orderType = 'trial'")
//    void updateFeedbackStatus(Long orderId, Integer status);
}
