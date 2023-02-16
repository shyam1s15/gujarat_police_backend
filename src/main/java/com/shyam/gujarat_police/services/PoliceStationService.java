package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.dto.response.DistrictTalukaAndPoliceStationNameRespDto;
import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.entities.PoliceStation;
import com.shyam.gujarat_police.exceptions.DataAlreadyExistException;
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
        if (isUniqueName(station.getPoliceStationName(), station.getPoliceStationNameInGujarati())){
            return policeStationRepository.save(station);
        } else {
            throw new DataAlreadyExistException("PoliceStation already exists " + station.getPoliceStationName());
        }
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
            obtainedPoliceStation.setPoliceStationName(policeStation.getPoliceStationName());
            if (policeStation.getHeadPolice() != null) {
                Police headPolice = policeRepository.findById(policeStation.getHeadPolice().getId())
                        .orElseThrow(() -> new DataNotFoundException("Head Police Not Found"));
                obtainedPoliceStation.setHeadPolice(headPolice);
            }
            return obtainedPoliceStation;
        }

    }

    public PoliceStation readSpecificById(Long stationId) {
        return policeStationRepository.findById(stationId)
                .orElseThrow(()->new DataNotFoundException("No station found for stationId: " + stationId));
    }

    public PoliceStation readSpecificByName(String stationName){
        List<PoliceStation> policeStationListFromExcel = policeStationRepository.findbyPoliceStationNameOrNameInGuj(stationName);
        if (policeStationListFromExcel.size() == 0 ){
            throw new DataNotFoundException("No station found for stationName: " + stationName);
        } else {
            return policeStationListFromExcel.get(0);
        }
    }
    public PoliceStation readSpecificByNameOrDemo(String stationName){
        List<PoliceStation> policeStationListFromExcel = policeStationRepository.findbyPoliceStationNameOrNameInGuj(stationName);
        if (policeStationListFromExcel.size() == 0 ){
            return policeStationRepository.findByPoliceStationName("demo").orElseThrow(()->
                    new DataNotFoundException("No station name found for station " + stationName));
        } else {
            return policeStationListFromExcel.get(0);
        }
    }

    public void deletePoliceStation(Long stationId){
        policeStationRepository.deleteById(stationId);
    }

    private boolean isUniqueName(String nameInGujarati, String nameInEnglish){
        Optional<PoliceStation> isPoliceStationExists = policeStationRepository.findByNameInGujaratiOrEnglish(nameInEnglish, nameInGujarati);
        return isPoliceStationExists.isEmpty();
    }

    public int saveMultiple(List<PoliceStation> policeStations){
        List<PoliceStation> uniquePoliceStation = policeStations.stream().
                filter(station -> !isPoliceStationExists(station)).toList();
        policeStationRepository.saveAll(uniquePoliceStation);
        return uniquePoliceStation.size();
    }

    public boolean isPoliceStationExists(PoliceStation station){
        return policeStationRepository.isStationExists(station);
    }
    public boolean isPoliceStationExists(String stationName){
        return policeStationRepository.isStationExists(stationName);
    }

    public List<DistrictTalukaAndPoliceStationNameRespDto> getDistrictTalukaAndPoliceStation() {
        return policeStationRepository.getDistrictTalukaAndPoliceStation();
    }
}
