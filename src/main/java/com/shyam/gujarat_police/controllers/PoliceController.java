package com.shyam.gujarat_police.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shyam.gujarat_police.dto.request.CreatePoliceDto;
import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.helper.ExcelHelper;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.ExcelService;
import com.shyam.gujarat_police.services.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/police")
public class PoliceController {

    @Autowired
    private PoliceService policeService;

    @Autowired
    private ExcelService excelService;

    @GetMapping("/")
    public APIResponse getAllPolice(){
        return APIResponse.ok(policeService.getAllPolice());
    }

    @PostMapping("/")
    public APIResponse savePolice(@RequestBody CreatePoliceDto police){
        Police savedPolice = policeService.savePolice(police);
        return APIResponse.ok(savedPolice);
    }

    @DeleteMapping("/{id}")
    public APIResponse deletePolice(@NotNull @PathVariable Long id){
        policeService.deletePolice(id);
        return APIResponse.ok("Police was successfully deleted");
    }

    @PutMapping("/{policeId}")
    public APIResponse updatePolice(@RequestBody @Valid @NotNull @NotBlank Police police,
                               @PathVariable("policeId") Long policeId){
        Police updatedPolice = policeService.updatePolice(police, policeId);
        return APIResponse.ok(updatedPolice);
    }

    @GetMapping("/{policeId}")
    public APIResponse readSpecific(@NotNull @PathVariable("policeId") Long policeId){
        Police police = policeService.readSpecific(policeId);
        return APIResponse.ok(police);
    }

    @GetMapping("/officer-data")
    public APIResponse officerData() throws JsonProcessingException {
        return policeService.officerData();
    }

    @PostMapping("/upload-from-excel")
    public APIResponse uploadFromExcel(@RequestParam("file") MultipartFile file,
                                       @RequestParam("event-id") Long eventId) throws IOException {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)){
            int totalPoliceInserted = excelService.savePoliceFromExcel(file, eventId);
            message = "Police Uploaded from Excel successfully " + totalPoliceInserted;
            return APIResponse.ok(message);
        }
        return APIResponse.ok("Something went wrong");
    }

    @GetMapping("/count")
    public APIResponse countPolice() throws JsonProcessingException {
        return policeService.countPolice();
    }

    @GetMapping("/count-by-event/{event-id}")
    public APIResponse countPoliceByEvent(@NotNull @PathVariable("event-id") Long eventId) {
        return policeService.countPoliceByEvent(eventId);
    }
}
