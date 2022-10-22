package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.AssignPolice;
import com.shyam.gujarat_police.services.AssignPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/assign-police")
public class AssignPoliceController {

    @Autowired
    private AssignPoliceService assignPoliceService;

    @GetMapping("/")
    public List<AssignPolice> getAllAssignedPolice(){
        return assignPoliceService.getAllAssignedPolice();
    }

    @PostMapping("/")
    public AssignPolice saveAssignPolice(@RequestBody @Valid AssignPolice assignPolice){
        return assignPoliceService.saveAssignPolice(assignPolice);
    }
}
