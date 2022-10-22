package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.AssignPolice;
import com.shyam.gujarat_police.repositories.AssignPoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignPoliceService {

    @Autowired
    private AssignPoliceRepository assignPoliceRepository;
    public List<AssignPolice> getAllAssignedPolice() {
        return (List<AssignPolice>) assignPoliceRepository.findAll();
    }

    public AssignPolice saveAssignPolice(AssignPolice assignPolice) {
        return assignPoliceRepository.save(assignPolice);
    }

}
