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
public class DesignationService {

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
            throw new DataNotFoundException("Designation not found: " + designationId);
        } else {
            Designation obtainedDesignation = designationOptional.get();
            obtainedDesignation.setName(designation.getName());
            return designationRepository.save(obtainedDesignation);
        }
    }

    public Designation getDesignationById(Long designationId){
        return designationRepository.findById(designationId)
                .orElseThrow(()-> new DataNotFoundException("Designation not found: " + designationId));
    }

    public Designation getDesignationByName(String designationName){
        return designationRepository.findByName(designationName)
                .orElseThrow(()-> new DataNotFoundException("Designation not found: " + designationName));
    }

    public Designation getDesignationByNameOrCreate(String designationName){
        return designationRepository.findByName(designationName)
                .orElseGet(() -> {
                    Designation designation = new Designation();
                    designation.setName(designationName);
                    designationRepository.save(designation);
                    return designation;
                });
    }

    public void deleteDesignation(Long designationId){
        designationRepository.deleteById(designationId);
    }
}
