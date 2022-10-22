package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.entities.PoliceStation;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.PoliceRepository;
import com.shyam.gujarat_police.repositories.PoliceStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoliceStationService {

    @Autowired
    private PoliceStationRepository policeStationRepository;

    @Autowired
    private PoliceRepository policeRepository;
    public List<PoliceStation> getAllPoliceStation() {
        return (List<PoliceStation>) policeStationRepository.findAll();
    }

    public PoliceStation savePoliceStation(PoliceStation station) {
        return policeStationRepository.save(station);
    }

    public PoliceStation updatePoliceStation(PoliceStation policeStation, Long stationId) {
        Optional<PoliceStation> optionalPoliceStation = policeStationRepository.findById(stationId);
        PoliceStation obtainedPoliceStation = null;
        if (optionalPoliceStation.isEmpty()) {
            throw new DataNotFoundException("Police Station not found");
        } else {
            obtainedPoliceStation = optionalPoliceStation.get();
            obtainedPoliceStation.setDistrict(policeStation.getDistrict());
            obtainedPoliceStation.setTaluko(policeStation.getTaluko());
            obtainedPoliceStation.setContactNumber(policeStation.getContactNumber());
            obtainedPoliceStation.setAddress(policeStation.getAddress());

            Police headPolice = policeRepository.findById(policeStation.getHeadPolice().getId())
                    .orElseThrow( ()->new DataNotFoundException("Head Police Not Found") );
            obtainedPoliceStation.setHeadPolice(headPolice);
            return obtainedPoliceStation;
        }

    }

    public PoliceStation readSpecific(Long stationId) {
        return policeStationRepository.findById(stationId)
                .orElseThrow(()->new DataNotFoundException("No station found for stationId: " + stationId));
    }
}
