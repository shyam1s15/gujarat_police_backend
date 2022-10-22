package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.Designation;
import com.shyam.gujarat_police.repositories.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationSerice {

    @Autowired
    private DesignationRepository designationRepository;
    public List<Designation> getAllDesignations() {
        return (List<Designation>) designationRepository.findAll();
    }

    public Designation saveDesignation(Designation designation) {
        return designationRepository.save(designation);
    }
}
