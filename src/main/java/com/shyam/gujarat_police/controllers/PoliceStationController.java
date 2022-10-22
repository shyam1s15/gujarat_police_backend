package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.PoliceStation;
import com.shyam.gujarat_police.services.PoliceStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/police-station")
public class PoliceStationController {

    @Autowired
    private PoliceStationService policeStationService;

    @GetMapping("/")
    public List<PoliceStation> getAllPoliceStation(){
        return policeStationService.getAllPoliceStation();
    }

    @PostMapping("/")
    public PoliceStation savePoliceStation(@RequestBody @Valid PoliceStation station){
        return policeStationService.savePoliceStation(station);
    }

    @PutMapping("/{stationId}")
    public PoliceStation updatePoliceStation(@RequestBody @Valid PoliceStation policeStation, @PathVariable("stationId") Long stationId){
        return policeStationService.updatePoliceStation(policeStation, stationId);
    }

    @GetMapping("/{stationId}")
    public PoliceStation readSpecific(@PathVariable("stationId") Long stationId){
        return policeStationService.readSpecific(stationId);
    }
}
