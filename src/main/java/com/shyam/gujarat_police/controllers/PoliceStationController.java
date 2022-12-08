package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.dto.response.DistrictTalukaAndPoliceStationNameRespDto;
import com.shyam.gujarat_police.entities.PoliceStation;
import com.shyam.gujarat_police.helper.ExcelHelper;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.ExcelService;
import com.shyam.gujarat_police.services.PoliceStationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public APIResponse getAllPoliceStation(){
        return APIResponse.ok(policeStationService.getAllPoliceStation());
    }

    @PostMapping("/")
    public APIResponse savePoliceStation(@RequestBody @Valid PoliceStation station){
        PoliceStation dto = policeStationService.savePoliceStation(station);
        return APIResponse.ok(dto);
    }

    @PutMapping("/{stationId}")
    public APIResponse updatePoliceStation(@RequestBody @Valid PoliceStation policeStation, @PathVariable("stationId") Long stationId){
        PoliceStation dto = policeStationService.updatePoliceStation(policeStation, stationId);
        return APIResponse.ok(dto);
    }

    @GetMapping("/{stationId}")
    public APIResponse readSpecific(@PathVariable("stationId") Long stationId){
        PoliceStation dto = policeStationService.readSpecificById(stationId);
        return APIResponse.ok(dto);
    }

    @DeleteMapping("/{stationId}")
    public APIResponse deleteSpecific(@PathVariable("stationId") Long stationId) {
        policeStationService.deletePoliceStation(stationId);
        return APIResponse.ok( "policeStation deleted successfully with id " + stationId );
    }
//JPZKvQGHuhJyGxE+6gU+/lncJMDIKl8W6VW5GlQe
    @PostMapping("/upload-from-excel")
    public APIResponse uploadFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)){
            int totalPoliceStationInserted = excelService.savePoliceStationFromExcel(file);
            message = "Police station Uploaded from Excel successfully " + totalPoliceStationInserted;
//            return ResponseEntity.ok(message);
            return APIResponse.ok(message);
        }
//        return ResponseEntity.ok("Something went wrong");
        return APIResponse.error(message);
    }

    @GetMapping("/district-taluka-station")
    public APIResponse getPoliceStationInDistTalukaAndName(){
        List<DistrictTalukaAndPoliceStationNameRespDto> dto = policeStationService.getDistrictTalukaAndPoliceStation();
        return APIResponse.ok(dto);

    }

}
