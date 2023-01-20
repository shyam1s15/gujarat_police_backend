package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.dto.request.EventPoliceCountDto;
import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.entities.EventPoliceCount;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.exceptions.DataSavingException;
import com.shyam.gujarat_police.repositories.EventPoliceCountRepository;
import com.shyam.gujarat_police.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
     * isSave boolean flag is used when saving is required other wise the method will act as an conversion
     * which will not save but return dto to model object
     */
    public EventPoliceCount saveEventPoliceCountIndividual(EventPoliceCountDto dto, Boolean isSave) {
        // check event exists
        // check designation exists
        designationService.getDesignationById(dto.getDesignationId());
        Event event = eventService.readSpecific(dto.getEventId());

        // TODO:event validation check in eventPoliceCount, if exists then data already there please try update methood

        EventPoliceCount eventPoliceCount = new EventPoliceCount();
        eventPoliceCount.setDesignationCount(dto.getDesignationCount());
        eventPoliceCount.setEvent(event);
        eventPoliceCount.setDesignationId(dto.getDesignationId());

        if (isSave){
            return eventPoliceCountRespository.save(eventPoliceCount);
        } else {
            return eventPoliceCount;
        }
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

    public List<EventPoliceCount> saveMultipleEventPoliceCount(Map<String, Object> body) {
        List<EventPoliceCount> resp = new ArrayList<>();

        Event event = eventService.readSpecific(Long.valueOf(body.get("event-id").toString()));
        Map<String, String> designationCounts = (Map<String, String>) body.get("designations");
        // TODO:event validation check in eventPoliceCount, if exists then data already there please try update methood
        boolean isEventAllocationExists = eventPoliceCountRespository.getAllByEvent(event.getId()).size() > 0;

        // data already exists
        if (isEventAllocationExists){
            throw new DataSavingException("Data already exists for event " + event.getEventName() + " try updating the event");
        }

        for (Map.Entry<String, String> designationCount : designationCounts.entrySet()){
            Long designationId = Long.parseLong(designationCount.getKey());
            Integer count = Integer.parseInt(designationCount.getValue());
            EventPoliceCountDto dto = new EventPoliceCountDto();
            dto.setEventId(event.getId());
            dto.setDesignationId(designationId);
            dto.setDesignationCount(count);
            EventPoliceCount eventPoliceCount = saveEventPoliceCountIndividual(dto, Boolean.FALSE);
            System.out.println(eventPoliceCount.getDesignationId());
            resp.add(eventPoliceCount);
        }
        return (List<EventPoliceCount>) CollectionUtil.makeCollection(eventPoliceCountRespository.saveAll(resp));
    }

    public List<EventPoliceCount> readAllByEvent(Long eventId) {
        // check event exists
        Event event = eventService.readSpecific(eventId);
        // check if data exists
        List<EventPoliceCount> data = eventPoliceCountRespository.getAllByEvent(eventId);
        if (data.size() == 0) {
            throw new DataNotFoundException("No data found for event " + event.getEventName());
        } else {
            return data;
        }
    }
}
