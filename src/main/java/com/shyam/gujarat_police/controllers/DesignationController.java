package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.Designation;
import com.shyam.gujarat_police.services.DesignationSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/designation")
public class DesignationController {

    @Autowired
    private DesignationSerice designationSerice;

    @GetMapping("/")
    public List<Designation> getAllDesignations() {
        return designationSerice.getAllDesignations();
    }

    @PostMapping("/")
    public Designation saveDesignation(@RequestBody @NotNull @Valid Designation designation) {
        return designationSerice.saveDesignation(designation);
    }

}
