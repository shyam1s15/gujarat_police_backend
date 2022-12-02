package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.PoliceStation;
import com.shyam.gujarat_police.helper.ExcelHelper;
import com.shyam.gujarat_police.services.ExcelService;
import com.shyam.gujarat_police.services.PoliceStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/police-station")
public class PoliceStationController {

    @Autowired
    private PoliceStationService policeStationService;


    @Autowired
    private ExcelService excelService;

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
        return policeStationService.readSpecificById(stationId);
    }

    @DeleteMapping("/{stationId}")
    public String deleteSpecific(@PathVariable("stationId") Long stationId) {
        policeStationService.deletePoliceStation(stationId);
        return "policeStation deleted successfully with id " + stationId;
    }

    @PostMapping("/{upload-from-excel}")
    public ResponseEntity<?> uploadFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)){
            excelService.savePoliceStationFromExcel(file);
            message = "Police station Uploaded from Excel successfully";
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.ok("Something went wrong");
    }
}
