package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.dto.request.*;
import com.shyam.gujarat_police.dto.response.EventPointPoliceAssignmentRespDto;
import com.shyam.gujarat_police.dto.response.EventPoliceAssignmentRespDto;
import com.shyam.gujarat_police.entities.AssignPolice;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.AssignPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/V2")
    public APIResponse saveAssignPoliceV2(@RequestBody AssignPoliceDto assignPolice){
        AssignPolice dto = assignPoliceService.saveAssignPoliceV2(assignPolice);
        return APIResponse.ok(dto);
    }

//    @PostMapping("/assign_police")
//    public APIResponse assignMultiplePolice(@RequestBody AssignMultiplePoliceDto dto){
//        APIResponse resp = assignPoliceService.assignMultiplePolice(dto);
//        return APIResponse.ok(resp);
//    }

    @PostMapping("/assign_multiple")
    public APIResponse saveAssignPoliceByPoliceIdList(@RequestBody AssignMultiplePoliceDto dto){
        return assignPoliceService.assignMultiplePolice(dto);

    }

    @GetMapping("/count-police-in-event")
    public APIResponse countPoliceInEvent(@RequestBody EventIdDto dto){
        Long respDto = assignPoliceService.countPoliceInEvent(dto);
        return APIResponse.ok(respDto);
    }

    @PostMapping("/count-police-in-point-event")
    public APIResponse countPoliceByEventAndPoint(@RequestBody EventAndPointIdDto dto){
        Long respDto = assignPoliceService.countPoliceByEventAndPoint(dto);
        return APIResponse.ok(respDto);
    }

    @PostMapping("/police-in-point-event")
    public APIResponse policeByEventAndPoint(@RequestBody EventAndPointIdDto dto){
        EventPointPoliceAssignmentRespDto resp = assignPoliceService.policeByEventAndPoint(dto);
        return APIResponse.ok(resp);
    }

    @PostMapping("/police-in-event")
    public APIResponse policeByEvent(@RequestBody EventAndPointIdDto dto){
        EventPoliceAssignmentRespDto resp = assignPoliceService.policeByEvent(dto);
        return APIResponse.ok(resp);
    }

    @PostMapping("by-designation")
    public APIResponse saveAssignPoliceByDesignation(@RequestBody AssignPoliceByDesignationCountDto dto){
        List<AssignPolice> respDto = assignPoliceService.saveAssignPoliceByDesignation(dto);
        return APIResponse.ok(respDto);
    }

    @GetMapping("point-assignment")
    public APIResponse savePointAssignment(@RequestBody AssignPoliceDto dto){
//        List<Police> resp = assignPoliceService.pointEventLevelAssignment(dto);
        Object o = assignPoliceService.pointEventLevelAssignment(dto);
        return APIResponse.ok(o);
    }
}
