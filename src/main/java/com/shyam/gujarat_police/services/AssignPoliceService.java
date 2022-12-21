package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.dto.request.AssignPoliceDto;
import com.shyam.gujarat_police.entities.AssignPolice;
import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.entities.Point;
import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.exceptions.DataSavingException;
import com.shyam.gujarat_police.exceptions.DateMisMatchException;
import com.shyam.gujarat_police.exceptions.PoliceAlreadyAssignedException;
import com.shyam.gujarat_police.repositories.AssignPoliceRepository;
import com.shyam.gujarat_police.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AssignPoliceService {

    @Autowired
    private AssignPoliceRepository assignPoliceRepository;

    @Autowired
    private PoliceService policeService;

    @Autowired
    private PointService pointService;

    @Autowired
    private EventService eventService;

    public List<AssignPolice> getAllAssignedPolice() {
        return (List<AssignPolice>) assignPoliceRepository.findAll();
    }

    public AssignPolice saveAssignPolice(AssignPoliceDto dto) {
        // if exists police, point and event.
        Police police = policeService.readSpecific(dto.getPoliceId());
        Point point = pointService.readSpecific(dto.getPointId());
        Event event = eventService.readSpecific(dto.getEventId());
        if (Objects.nonNull(police)
                && Objects.nonNull(point)
                && Objects.nonNull(event)) {
            AssignPolice assignPolice = new AssignPolice();
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getAssignedDate())) {
                assignPolice.setAssignedDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getAssignedDate()));
            }
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyStartDate())) {
                assignPolice.setDutyStartDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyStartDate()));
            }
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyEndDate())) {
                assignPolice.setDutyEndDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyEndDate()));
            }

            assignPolice.setPolice(police);
            assignPolice.setPoint(point);
            assignPolice.setEvent(event);
            if (!isPoliceAssigned(assignPolice)){
                return assignPoliceRepository.save(assignPolice);
            } else {
                 throw new PoliceAlreadyAssignedException("Police already assigned");
            }
        } else {
            throw new DataSavingException("Could not save assigned police information with police id " + dto.getPoliceId());
        }

    }


    public AssignPolice saveAssignPoliceV2(AssignPoliceDto dto) {
        List<Event> eventsBetweenGivenEvent = eventService.getEventsBetweenGivenEvent(dto.getEventId());
        List<Long> eventIds = eventsBetweenGivenEvent.stream().map(Event::getId).toList();
        // if exists police, point and event.
        Police police = policeService.readSpecific(dto.getPoliceId());
        Point point = pointService.readSpecific(dto.getPointId());
        Event event = eventService.readSpecific(dto.getEventId());
        if (Objects.nonNull(police)
                && Objects.nonNull(point)
                && Objects.nonNull(event)) {
            AssignPolice assignPolice = new AssignPolice();
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getAssignedDate())) {
                assignPolice.setAssignedDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getAssignedDate()));
            }
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyStartDate())) {
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyStartDate()));               // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, 6);      // adds one hour
                assignPolice.setDutyStartDate(cal.getTime());
            }
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyEndDate())) {
                assignPolice.setDutyEndDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyEndDate()));
            }

            // check if duty dates is in range of event date.
            if (!(event.getEventStartDate().before(assignPolice.getDutyStartDate()) && event.getEventEndDate().after(assignPolice.getDutyEndDate()))) {
                throw new DateMisMatchException("Date is not in range of event date : " + event.getEventStartDate() + " : " + event.getEventEndDate() + " police assignment dates : " + assignPolice.getDutyStartDate() + " " + assignPolice.getDutyEndDate());
            }

            assignPolice.setPolice(police);
            assignPolice.setPoint(point);
            assignPolice.setEvent(event);

            if (!isPoliceAssignedV2(assignPolice, eventIds)){
                System.out.println(eventsBetweenGivenEvent);
                return assignPoliceRepository.save(assignPolice);
//                return assignPolice;
            } else {
                throw new PoliceAlreadyAssignedException("Police already assigned");
            }
        } else {
            throw new DataSavingException("Could not save assigned police information with police id " + dto.getPoliceId());
        }
    }


    public AssignPolice updateAssignPolice(AssignPolice assignPolice, Long assignPoliceId) {
        if (Objects.isNull(assignPoliceId)) {
            throw new DataNotFoundException("assignPoliceId is null for id " + assignPoliceId);
        }
        Optional<AssignPolice> assignPoliceOptional = assignPoliceRepository.findById(assignPoliceId);
        if (assignPoliceOptional.isEmpty()) {
            throw new DataNotFoundException("assignPolice is not found for id " + assignPoliceId);
        } else {
            AssignPolice obtainedAssignPolice = assignPoliceOptional.get();
            obtainedAssignPolice.setPolice(assignPolice.getPolice());
            obtainedAssignPolice.setEvent(assignPolice.getEvent());
            obtainedAssignPolice.setPoint(assignPolice.getPoint());
            obtainedAssignPolice.setAssignedDate(assignPolice.getAssignedDate());
            return assignPoliceRepository.save(obtainedAssignPolice);
        }
    }

    public AssignPolice readSpecific(Long assignPoliceId) {
        return assignPoliceRepository.findById(assignPoliceId)
                .orElseThrow(() -> new DataNotFoundException("AssignPolice not found with id: " + assignPoliceId));
    }

    public void deletePolice(Long assignPoliceId) {
        assignPoliceRepository.deleteById(assignPoliceId);
    }


    private boolean isPoliceAssigned(AssignPolice assignPolice) {
        // if police not in assign police then assign
        // if duty start date > event end date then assign
        // if duty start date > last event end date then assign

        boolean result = assignPoliceRepository.isPoliceAssignedForSpecificEvent(assignPolice.getPolice().getId() ,assignPolice.getDutyStartDate(), assignPolice.getEvent().getId());
        if (Objects.nonNull(result)){
            return result;
        } else {
            return false;
        }
    }

    private boolean isPoliceAssignedV2(AssignPolice assignPolice, List<Long> eventIds) {
        // if police not in assign police then assign
        // if duty start date > event end date then assign
        // if duty start date > last event end date then assign
        System.out.println(eventIds);
        System.out.println("duty start date: " + assignPolice.getDutyStartDate());
        int result = assignPoliceRepository.isPoliceAssignedForSpecificEvents(assignPolice.getPolice().getId() ,assignPolice.getDutyStartDate(), eventIds);
        System.out.println(result);
        return result > 0;
    }

}
