package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.dto.request.AssignPoliceByDesignationCountDto;
import com.shyam.gujarat_police.dto.request.AssignPoliceDto;
import com.shyam.gujarat_police.dto.request.EventAndPointIdDto;
import com.shyam.gujarat_police.dto.request.EventIdDto;
import com.shyam.gujarat_police.dto.response.DesignationCountRespDto;
import com.shyam.gujarat_police.dto.response.EventPointPoliceCountAssignmentRespDto;
import com.shyam.gujarat_police.entities.*;
import com.shyam.gujarat_police.exceptions.*;
import com.shyam.gujarat_police.repositories.AssignPoliceRepository;
import com.shyam.gujarat_police.repositories.PoliceRepository;
import com.shyam.gujarat_police.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Autowired
    private DesignationService designationService;

    @Autowired
    private PointPoliceCountService pointPoliceCountService;

    @Autowired
    private PoliceRepository policeRepository;

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
        // past code
//        int result = assignPoliceRepository.isPoliceAssignedForSpecificEvents(assignPolice.getPolice().getId() ,assignPolice.getDutyStartDate(), eventIds);
        return assignPoliceRepository.isPoliceAssignedForSpecificEvents(assignPolice.getPolice().getId() ,assignPolice.getDutyStartDate(), eventIds);
//        System.out.println(result);
//        return result > 0;
    }


    public Long countPoliceInEvent(EventIdDto dto) {
        // check if event exists else throw
        Event eventOptional = eventService.readSpecific(dto.getEventId());
        return assignPoliceRepository.countPoliceInEvent(dto.getEventId());
    }

    public Long countPoliceByEventAndPoint(EventAndPointIdDto dto) {
        // check if event exists else throw
        Event eventOptional = eventService.readSpecific(dto.getEventId());
        return assignPoliceRepository.countPoliceByEventAndPoint(dto.getEventId(), dto.getPointId());
    }

    /**
     * @author shyam
     *
     *
     * */
//    public List<AssignPolice> saveAssignPoliceByDesignation(AssignPoliceByDesignationCountDto dto) {
//        // check if event exists
//        Event event = eventService.readSpecific(dto.getEventId());
//        // check if point exists
//        Point point = pointService.readSpecific(dto.getPointId());
//
//        // check if designation exists
//        Designation designation = designationService.getDesignationByNameOrNameInGujarati(dto.getDesignationName());
////        Date dutyStartDate = new Date();
//        Date dutyStartDate = DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyStartDate());
//        Date dutyEndDate = DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyEndDate());
//        Date assignedDate = DateUtil.stringToDate("dd/MM/yyyy", dto.getAssignedDate());
//
//        // find available police in these designations
//        // we have to first find all police with designation and search them all
////        List<Long> policeWithDesignationIds = designation.getPolice().stream().map(Police::getId).toList();
//        List<Police> policeWithDesignation = designation.getPolice();
////        List<Police> policeWithDesignation = designation.getPolice().stream().filter(p -> p.getEvent().getId().equals(event.getId())).toList();
//
//
//        if (policeWithDesignation.size() == 0 ){
//            throw new DataNotFoundException("No police associated with designation " + designation.getName() );
//        }
//
//        // get all events occuring between given event.
//        List<Event> eventsBetweenGivenEvent = eventService.getEventsBetweenGivenEvent(dto.getEventId());
//        List<Long> eventIds = eventsBetweenGivenEvent.stream().map(Event::getId).toList();
//
////        if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyStartDate())) {
////            Calendar cal = Calendar.getInstance(); // creates calendar
////            cal.setTime(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyStartDate()));               // sets calendar time/date
////            cal.add(Calendar.HOUR_OF_DAY, 6);      // adds one hour
////            dutyStartDate = cal.getTime();
////        }
//        // check if these police are free
//        List<Police> availablePoliceIdsWithDesignation = new ArrayList<>(policeWithDesignation.stream().filter(police -> !assignPoliceRepository.isPoliceAssignedForSpecificEvents(police.getId(), dutyStartDate, eventIds)).toList());
//        if (dto.getPoliceCount() > availablePoliceIdsWithDesignation.size()) {
//            throw new InsufficientDataException("Insufficient police Only " + availablePoliceIdsWithDesignation.size() + " police is available out of " + dto.getPoliceCount());
//        }
//        Collections.shuffle(availablePoliceIdsWithDesignation);
//        List<AssignPolice> assignedPoliceList = new ArrayList<AssignPolice>();
//
//        for(int i = 0; i < dto.getPoliceCount(); i++){
//            AssignPolice assignPolice = new AssignPolice();
//            assignPolice.setEvent(event);
//            assignPolice.setPoint(point);
//            assignPolice.setDutyStartDate(dutyStartDate);
//            assignPolice.setDutyEndDate(dutyEndDate);
//            assignPolice.setAssignedDate(assignedDate);
//            assignPolice.setPolice(availablePoliceIdsWithDesignation.get(i));
//            assignedPoliceList.add(assignPolice);
//        }
//        assignPoliceRepository.saveAll(assignedPoliceList);
//        return assignedPoliceList;
//    }

    public List<AssignPolice> saveAssignPoliceByDesignation(AssignPoliceByDesignationCountDto dto) {
        // check if event exists
        Event event = eventService.readSpecific(dto.getEventId());
        // check if point exists
        Point point = pointService.readSpecific(dto.getPointId());

        // check if designation exists
        Designation designation = designationService.getDesignationByNameOrNameInGujarati(dto.getDesignationName());
//        Date dutyStartDate = new Date();
        Date dutyStartDate = DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyStartDate());
        Date dutyEndDate = DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyEndDate());
        Date assignedDate = DateUtil.stringToDate("dd/MM/yyyy", dto.getAssignedDate());

        // find available police in these designations
        // we have to first find all police with designation and search them all
//        List<Long> policeWithDesignationIds = designation.getPolice().stream().map(Police::getId).toList();
        List<Police> policeWithDesignation = designation.getPolice();
//        List<Police> policeWithDesignation = designation.getPolice().stream().filter(p -> p.getEvent().getId().equals(event.getId())).toList();


        if (policeWithDesignation.size() == 0 ){
            throw new DataNotFoundException("No police associated with designation " + designation.getName() );
        }

        // get all events occuring between given event.
        List<Event> eventsBetweenGivenEvent = eventService.getEventsBetweenGivenEvent(dto.getEventId());
        List<Long> eventIds = eventsBetweenGivenEvent.stream().map(Event::getId).toList();

//        if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyStartDate())) {
//            Calendar cal = Calendar.getInstance(); // creates calendar
//            cal.setTime(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyStartDate()));               // sets calendar time/date
//            cal.add(Calendar.HOUR_OF_DAY, 6);      // adds one hour
//            dutyStartDate = cal.getTime();
//        }
        // check if these police are free
        List<Police> availablePoliceIdsWithDesignation = new ArrayList<>(policeWithDesignation.stream().filter(police -> !assignPoliceRepository.isPoliceAssignedForSpecificEvents(police.getId(), dutyStartDate, eventIds)).toList());
        if (dto.getPoliceCount() > availablePoliceIdsWithDesignation.size()) {
            throw new InsufficientDataException("Insufficient police Only " + availablePoliceIdsWithDesignation.size() + " police is available out of " + dto.getPoliceCount());
        }
        Collections.shuffle(availablePoliceIdsWithDesignation);
        List<AssignPolice> assignedPoliceList = new ArrayList<AssignPolice>();

        for(int i = 0; i < dto.getPoliceCount(); i++){
            AssignPolice assignPolice = new AssignPolice();
            assignPolice.setEvent(event);
            assignPolice.setPoint(point);
            assignPolice.setDutyStartDate(dutyStartDate);
            assignPolice.setDutyEndDate(dutyEndDate);
            assignPolice.setAssignedDate(assignedDate);
            assignPolice.setPolice(availablePoliceIdsWithDesignation.get(i));
            assignedPoliceList.add(assignPolice);
        }
        assignPoliceRepository.saveAll(assignedPoliceList);
        return assignedPoliceList;
    }

    public Object pointEventLevelAssignment(AssignPoliceDto dto){
        Event event = eventService.readSpecific(dto.getEventId());
        Point point = pointService.readSpecific(dto.getPointId());

        EventPointPoliceCountAssignmentRespDto pointLevelAssignmentDto =
                pointPoliceCountService.readAllByPointDesignationCountsAndEventName(event.getId(), point.getId());

        List<DesignationCountRespDto> designationCountRespDtoList = pointLevelAssignmentDto.getAssignments();
//        System.out.println(designationCountRespDtoList.get(0).getDesignationId());
        AtomicInteger totalAssignedPolice = new AtomicInteger();
        designationCountRespDtoList.forEach(d -> {
            List<Police> assignedPolice = assignByDesignationCountForPoint(d, dto);
            totalAssignedPolice.addAndGet(assignedPolice.size());
        });
        return totalAssignedPolice;
//        return assignByDesignationCountForPoint(designationCountRespDtoList.get(0), dto);
//        designationCountRespDtoList.get(0).get


    }
    private List<Police> assignByDesignationCountForPoint(DesignationCountRespDto designationCount, AssignPoliceDto dto){
        Point point = pointService.readSpecific(dto.getPointId());
        Event event = eventService.readSpecific(dto.getEventId());
        // check if police is already assigned for specified designation
        Integer previousAssignment = assignPoliceRepository.getPreviousAssignmentForDesignation(dto.getEventId(), dto.getPointId(), designationCount.getDesignationId());
        // find free police of event's and of designation which are unassigned
        int requiredCount = designationCount.getDesignationCount()-previousAssignment;
        List<Police> unassignedPoliceOfDesignation = policeService.getUnassignedPoliceOfDesignation(dto.getEventId(),designationCount.getDesignationId());
        // if required police are more then throw error
        if(requiredCount > unassignedPoliceOfDesignation.size()){
            throw new InsufficientDataException("Unavailable police with designation count " + designationCount.getDesignationCount() + " only available are " + unassignedPoliceOfDesignation.size());
        }
        List<Police> policeToBeAssignedList = new ArrayList<Police>();
        List<AssignPolice> assignPoliceList = new ArrayList<AssignPolice>();

        Collections.shuffle(unassignedPoliceOfDesignation);
        for (int i = 0; i < requiredCount; i++) {
            Police policeToBeAssigned = unassignedPoliceOfDesignation.get(i);
            policeToBeAssigned.setAssigned(true);

            AssignPolice assignPolice = new AssignPolice();
            assignPolice.setPoint(point);
            assignPolice.setEvent(event);
            assignPolice.setPolice(policeToBeAssigned);

            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getAssignedDate())) {
                assignPolice.setAssignedDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getAssignedDate()));
            }
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyStartDate())) {
                assignPolice.setDutyStartDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyStartDate()));
            }
            if (DateUtil.isValidFormat("dd/MM/yyyy", dto.getDutyEndDate())) {
                assignPolice.setDutyEndDate(DateUtil.stringToDate("dd/MM/yyyy", dto.getDutyEndDate()));
            }
            policeToBeAssignedList.add(policeToBeAssigned);
            assignPoliceList.add(assignPolice);
        }
        policeRepository.saveAll(policeToBeAssignedList);
        assignPoliceRepository.saveAll(assignPoliceList);
        return policeToBeAssignedList;
//        return unassignedPoliceOfDesignation;
    }
}
