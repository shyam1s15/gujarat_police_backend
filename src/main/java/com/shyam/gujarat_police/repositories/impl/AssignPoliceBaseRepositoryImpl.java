package com.shyam.gujarat_police.repositories.impl;

import com.shyam.gujarat_police.dto.request.DesignationCountDto;
import com.shyam.gujarat_police.entities.AssignPolice;
import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.repositories.AssignPoliceBaseRepository;
import com.shyam.gujarat_police.repositories.BaseRepository;

import java.util.Date;
import java.util.List;

public class AssignPoliceBaseRepositoryImpl extends BaseRepository<AssignPolice> implements AssignPoliceBaseRepository {

    @Override
    public List<Police> findFreePoliceOrNotAssigned(Date eventDate) {
//        String queryString = "select p from Police p " +
//                "left join AssignPolice ap on ap.id = p.id " +
        return null;
    }

    @Override
    public List<Police> findFreePoliceOrNotAssignedByDesignation(DesignationCountDto designationCountDto) {
        return null;
    }
}
