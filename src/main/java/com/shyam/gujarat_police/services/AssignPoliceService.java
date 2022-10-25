package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.AssignPolice;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.AssignPoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public AssignPolice updateAssignPolice(AssignPolice assignPolice, Long assignPoliceId) {
        if (Objects.isNull(assignPoliceId)) {
            throw new DataNotFoundException("assignPoliceId is null for id " + assignPoliceId);
        }
        Optional<AssignPolice> assignPoliceOptional = assignPoliceRepository.findById(assignPoliceId);
        if (assignPoliceOptional.isEmpty()) {
            throw new DataNotFoundException("assignPolice is not found for id " + assignPoliceId);
        } else {
            AssignPolice obtainedAssignPolice = assignPoliceOptional.get();
            obtainedAssignPolice.setPolice(assignPolice.getPolice());
            obtainedAssignPolice.setEvent(assignPolice.getEvent());
            obtainedAssignPolice.setPoint(assignPolice.getPoint());
            obtainedAssignPolice.setAssignedDate(assignPolice.getAssignedDate());
            return assignPoliceRepository.save(obtainedAssignPolice);
        }
    }

    public AssignPolice readSpecific(Long assignPoliceId) {
        return assignPoliceRepository.findById(assignPoliceId)
                .orElseThrow(()->new DataNotFoundException("AssignPolice not found with id: " + assignPoliceId));
    }

    public void deletePolice(Long assignPoliceId){
        assignPoliceRepository.deleteById(assignPoliceId);
    }
}
