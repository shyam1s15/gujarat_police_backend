package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.dto.request.EventPoliceCountDto;
import com.shyam.gujarat_police.entities.EventPoliceCount;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.EventPoliceCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/event_police_count")
public class EventPoliceCountController {

    @Autowired
    private EventPoliceCountService eventPoliceCountService;

    @GetMapping("/")
    public APIResponse getAllEventsPoliceCount(){
        List<EventPoliceCount> resp = eventPoliceCountService.getAllEventPoliceCount();
        return APIResponse.ok(resp);
    }

//    @PostMapping("/")
//    public APIResponse saveEventPoliceCount(@RequestParam(value = "event-id") Long eventId, @RequestParam Map<Long, Long> designationCount){
    @RequestMapping(value="/",
        method=RequestMethod.POST)
    public APIResponse saveMultipleEventPoliceCount(@RequestBody Map<String,Object> body){
        List<EventPoliceCount> resp = eventPoliceCountService.saveMultipleEventPoliceCount(body);
        return APIResponse.ok(resp);
    }

    public APIResponse saveEventPoliceCountIndividual(@RequestBody EventPoliceCountDto dto){
        EventPoliceCount resp = eventPoliceCountService.saveEventPoliceCountIndividual(dto, Boolean.TRUE);
        return APIResponse.ok(resp);
    }

    @PutMapping("/{eventPoliceCountId}")
    public APIResponse updateEventPoliceCount(@RequestBody @Valid EventPoliceCountDto eventPoliceCount,
                                   @PathVariable("eventPoliceCountId") Long eventPoliceCountId){
        EventPoliceCount dto = eventPoliceCountService.updateEventPoliceCount(eventPoliceCount, eventPoliceCountId);
        return APIResponse.ok(dto);
    }

    @DeleteMapping("/{eventPoliceCountId}")
    public APIResponse deleteEventPoliceCount(@PathVariable("eventPoliceCountId") Long eventPoliceCountId){
        eventPoliceCountService.deleteEventPoliceCount(eventPoliceCountId);
        return APIResponse.ok("Event deleted successfully with id " + eventPoliceCountId);
    }

//    @GetMapping("/{eventPoliceCountId}")
//    public APIResponse readSpecific(@PathVariable("eventPoliceCountId") Long eventPoliceCountId){
//        EventPoliceCount dto = eventPoliceCountService.readSpecific(eventPoliceCountId);
//        return APIResponse.ok(dto);
//    }

    @GetMapping("/{eventId}")
    public APIResponse readAllByEvent(@PathVariable("eventId") Long eventId){
        List<EventPoliceCount> dto = eventPoliceCountService.readAllByEvent(eventId);
        return APIResponse.ok(dto);
    }
}
