package com.shyam.gujarat_police.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.helper.ExcelHelper;
import com.shyam.gujarat_police.services.ExcelService;
import com.shyam.gujarat_police.services.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/police")
public class PoliceController {

    @Autowired
    private PoliceService policeService;

    @Autowired
    private ExcelService excelService;

    @GetMapping("/")
    public List<Police> getAllPolice(){
        return policeService.getAllPolice();
    }

    @PostMapping("/")
    public Police savePolice(@RequestBody @Valid @NotNull @NotBlank Police police){
        return policeService.savePolice(police);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePolice(@NotNull @PathVariable Long id){
        policeService.deletePolice(id);
        return ResponseEntity.ok("Police was successfully deleted");
    }

    @PutMapping("/{policeId}")
    public Police updatePolice(@RequestBody @Valid @NotNull @NotBlank Police police,
                               @PathVariable("policeId") Long policeId){
        return policeService.updatePolice(police, policeId);
    }

    @GetMapping("/{policeId}")
    public Police readSpecific(@NotNull @PathVariable("policeId") Long policeId){
        return policeService.readSpecific(policeId);
    }

    @GetMapping("/officer-data")
    public ResponseEntity<?> officerData() throws JsonProcessingException {
        return policeService.officerData();
    }

    @PostMapping("/upload-from-excel")
    public ResponseEntity<?> uploadFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)){
            excelService.savePoliceFromExcel(file);
            message = "Police Uploaded from Excel successfully";
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.ok("Something went wrong");
    }

}
