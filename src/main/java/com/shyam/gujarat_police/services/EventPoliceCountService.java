package com.shyam.gujarat_police.services;

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
    public List<EventPoliceCount> getAllEventPoliceCount() {
        return (List<EventPoliceCount>) eventPoliceCountRespository.findAll();
    }

    public EventPoliceCount saveEventPoliceCount(EventPoliceCount eventPoliceCount) {
        // check event exists
        // check designation exists
        return eventPoliceCountRespository.save(eventPoliceCount);
    }

    public EventPoliceCount updateEventPoliceCount(EventPoliceCount eventPoliceCount, Long eventPoliceCountId) {
        if (Objects.isNull(eventPoliceCountId)){
            throw new DataNotFoundException("EventPoliceCount id not found with id " + eventPoliceCountId);
        }
        Optional<EventPoliceCount> optionalEventPoliceCount = eventPoliceCountRespository.findById(eventPoliceCountId);
        if (optionalEventPoliceCount.isEmpty()) {
            throw new DataNotFoundException("EventPoliceCount not found with id " + eventPoliceCountId);
        } else {
            EventPoliceCount obtainedObj = optionalEventPoliceCount.get();
            obtainedObj.setDesignation_count(eventPoliceCount.getDesignation_count());
            obtainedObj.setDesignation_name(eventPoliceCount.getDesignation_name());
            obtainedObj.setEvent(eventPoliceCount.getEvent());
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
