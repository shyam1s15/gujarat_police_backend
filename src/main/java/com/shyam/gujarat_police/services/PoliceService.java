package com.shyam.gujarat_police.services;


import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.PoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PoliceService {

    @Autowired
    private PoliceRepository policeRepository;

    public List<Police> getAllPolice() {
        return (List<Police>) policeRepository.findAll();
    }

    public Police savePolice(Police police) {
        return policeRepository.save(police);
    }

    public void deletePolice(Long id) {
        policeRepository.delete(policeRepository.findById(id).get());
    }

    public Police updatePolice(Police police, Long policeId) {
        if (Objects.isNull(policeId)){
            throw new DataNotFoundException("Police Id not found with id " + policeId);
        }
        Optional<Police> optionalPolice = policeRepository.findById(policeId);
        if (optionalPolice.isEmpty()) {
            throw new DataNotFoundException("Police not found with id " + policeId);
        } else {
            Police obtainedPolice = optionalPolice.get();
            obtainedPolice.setFullName(police.getFullName());
            obtainedPolice.setBuckleNumber(police.getBuckleNumber());
            obtainedPolice.setNumber(police.getNumber());
            obtainedPolice.setAge(police.getAge());
            obtainedPolice.setDistrict(police.getDistrict());
            obtainedPolice.setGender(police.getGender());
            obtainedPolice.setDesignation(police.getDesignation());
            obtainedPolice.setPoliceStation(police.getPoliceStation());
            return policeRepository.save(obtainedPolice);
        }
    }

    public Police readSpecific(Long policeId) {
        return policeRepository.findById(policeId)
                .orElseThrow(()->new DataNotFoundException("Police not found with id: " + policeId));
    }

}
