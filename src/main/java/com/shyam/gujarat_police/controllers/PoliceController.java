package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.services.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/police")
public class PoliceController {

    @Autowired
    private PoliceService policeService;

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
}
