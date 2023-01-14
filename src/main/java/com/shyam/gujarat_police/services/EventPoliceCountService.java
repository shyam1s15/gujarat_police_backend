package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.dto.request.EventPoliceCountDto;
import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.entities.EventPoliceCount;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.EventPoliceCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventPoliceCountService {

    @Autowired
    private EventPoliceCountRepository eventPoliceCountRespository;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private EventService eventService;

    public List<EventPoliceCount> getAllEventPoliceCount() {
        return (List<EventPoliceCount>) eventPoliceCountRespository.findAll();
    }

    public EventPoliceCount saveEventPoliceCount(EventPoliceCountDto dto) {
        // check event exists
        // check designation exists
        designationService.getDesignationById(dto.getDesignationId());
        Event event = eventService.readSpecific(dto.getEventId());


        EventPoliceCount eventPoliceCount = new EventPoliceCount();
        eventPoliceCount.setDesignationCount(dto.getDesignationCount());
        eventPoliceCount.setEvent(event);
        eventPoliceCount.setDesignationId(dto.getDesignationId());
        return eventPoliceCountRespository.save(eventPoliceCount);
    }

    public EventPoliceCount updateEventPoliceCount(EventPoliceCountDto dto, Long eventPoliceCountId) {
        if (Objects.isNull(eventPoliceCountId)){
            throw new DataNotFoundException("EventPoliceCount id not found with id " + eventPoliceCountId);
        }
        Optional<EventPoliceCount> optionalEventPoliceCount = eventPoliceCountRespository.findById(eventPoliceCountId);
        if (optionalEventPoliceCount.isEmpty()) {
            throw new DataNotFoundException("EventPoliceCount not found with id " + eventPoliceCountId);
        } else {
            EventPoliceCount obtainedObj = optionalEventPoliceCount.get();

            // check event exists
            // check designation exists
            designationService.getDesignationById(dto.getDesignationId());
            Event event = eventService.readSpecific(dto.getEventId());

            obtainedObj.setDesignationCount(dto.getDesignationCount());
            obtainedObj.setDesignationId(dto.getDesignationId());
            obtainedObj.setEvent(event);
            return eventPoliceCountRespository.save(obtainedObj);
        }
    }

    public void deleteEventPoliceCount(Long eventPoliceCountId) {
        eventPoliceCountRespository.deleteById(eventPoliceCountId);
    }

    public EventPoliceCount readSpecific(Long eventPoliceCountId) {
        return eventPoliceCountRespository.findById(eventPoliceCountId)
                .orElseThrow(()-> new DataNotFoundException("EventPoliceCount not found with id: " + eventPoliceCountId) );
    }
}
