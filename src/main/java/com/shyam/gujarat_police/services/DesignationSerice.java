package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.Designation;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Designation updateDesignation(Designation designation, Long designationId) {
        if (Objects.nonNull(designationId)){
            throw new DataNotFoundException("Designation Id not found: " + designationId);
        }
        Optional<Designation> designationOptional = designationRepository.findById(designationId);
        if ( designationOptional.isEmpty() ) {
            throw new DataNotFoundException("Designation Id not found: " + designationId);
        } else {
            Designation obtainedDesignation = designationOptional.get();
            obtainedDesignation.setName(designation.getName());
            return designationRepository.save(obtainedDesignation);
        }
    }

    public Designation getDesignation(Long designationId){
        return designationRepository.findById(designationId)
                .orElseThrow(()-> new DataNotFoundException("Designation not found: " + designationId));
    }

    public void deleteDesignation(Long designationId){
        designationRepository.deleteById(designationId);
    }
}
