package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.dto.request.FindByDesignationDto;
import com.shyam.gujarat_police.entities.Designation;
import com.shyam.gujarat_police.response.APIResponse;
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
    public APIResponse getAllDesignations() {
        return APIResponse.ok(designationService.getAllDesignations());
    }

    @PostMapping("/")
    public APIResponse saveDesignation(@RequestBody @NotNull @Valid Designation designation) {
        Designation dto = designationService.saveDesignation(designation);
        return APIResponse.ok(dto);
    }

    @PutMapping("/{designationId}")
    public APIResponse updateDesignation(@RequestBody FindByDesignationDto designation,
            @PathVariable("designationId") Long designationId){
        Designation dto = designationService.updateDesignation(designation, designationId);
        return APIResponse.ok(dto);
    }

    @GetMapping("/{designationId}")
    public APIResponse readSpecific(@PathVariable("designationId") Long designationId){
        Designation dto = designationService.getDesignationById(designationId);
        return APIResponse.ok(dto);
    }

    @DeleteMapping("/{designationId}")
    public APIResponse deleteDesignation(@PathVariable("designationId") Long designationId){
        designationService.deleteDesignation(designationId);
        return APIResponse.ok("Designation deleted successfully with id " + designationId);
    }

<<<<<<< HEAD
    @GetMapping("/find-by")
    public APIResponse findByDesignation(@RequestBody FindByDesignationDto dto){
        List<Designation> resp = designationService.findInDesignation(dto);
        return APIResponse.ok(resp);
    }
=======
>>>>>>> master
}
