package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.InternalPurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/internal_purpose")
public class InternalPurpose {

    @Autowired
    private InternalPurposeService internalPurposeService;

    @DeleteMapping("/assign-police/reset-event-police/{event-id}")
    public APIResponse deleteAssignmentsInEntireEvent(@PathVariable Long eventId) {
        return internalPurposeService.deleteAssignmentsInEntireEvent(eventId);
    }
}
