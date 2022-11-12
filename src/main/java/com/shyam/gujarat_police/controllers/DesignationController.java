package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.Designation;
import com.shyam.gujarat_police.services.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/designation")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping("/")
    public List<Designation> getAllDesignations() {
        return designationService.getAllDesignations();
    }

    @PostMapping("/")
    public Designation saveDesignation(@RequestBody @NotNull @Valid Designation designation) {
        return designationService.saveDesignation(designation);
    }

    @PutMapping("/{designationId}")
    public Designation updateDesignation(@RequestBody @NotNull @Valid Designation designation,
            @PathVariable("designationId") Long designationId){
        return designationService.updateDesignation(designation, designationId);
    }

    @GetMapping("/{designationId}")
    public Designation readSpecific(@PathVariable("designationId") Long designationId){
        return designationService.getDesignationById(designationId);
    }

    @DeleteMapping("/{designationId}")
    public String deleteDesignation(@PathVariable("designationId") Long designationId){
        designationService.deleteDesignation(designationId);
        return "Designation deleted successfully with id " + designationId;
    }
}
