package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.dto.request.AssignPoliceDto;
import com.shyam.gujarat_police.entities.AssignPolice;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.AssignPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/assign-police")
public class AssignPoliceController {

    @Autowired
    private AssignPoliceService assignPoliceService;

    @GetMapping("/")
    public APIResponse getAllAssignedPolice(){
        return APIResponse.ok(assignPoliceService.getAllAssignedPolice());
    }

    @PostMapping("/")
    public APIResponse saveAssignPolice(@RequestBody AssignPoliceDto assignPolice){
        AssignPolice dto = assignPoliceService.saveAssignPolice(assignPolice);
        return APIResponse.ok(dto);
    }
}
