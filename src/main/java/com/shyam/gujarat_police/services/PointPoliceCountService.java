package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.dto.request.DesignationDto;
import com.shyam.gujarat_police.dto.request.PointPoliceCountDto;
import com.shyam.gujarat_police.dto.response.DesignationCountRespDto;
import com.shyam.gujarat_police.dto.response.EventPointPoliceCountAssignmentRespDto;
import com.shyam.gujarat_police.dto.response.EventPoliceCountAssignmentRowDto;
import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.entities.Point;
import com.shyam.gujarat_police.entities.PointPoliceCount;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.exceptions.DataSavingException;
import com.shyam.gujarat_police.repositories.PointPoliceCountRepository;
import com.shyam.gujarat_police.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PointPoliceCountService {
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private PointService pointService;
    
    @Autowired
    private DesignationService designationService;
    
    @Autowired
    private PointPoliceCountRepository pointPoliceCountRepository;
    
    public List<PointPoliceCount> getAllPointPoliceCount() {
        return (List<PointPoliceCount>) pointPoliceCountRepository.findAll();
    }

    public List<PointPoliceCount> getAllPointCountsByEvent(Long eventId) {
        return pointPoliceCountRepository.findByEventId(eventId);
    }
    public PointPoliceCount savePointPoliceCountIndividual(PointPoliceCountDto dto, Boolean isSave) {
        // check event exists
        // check designation exists
        // check point exists
        designationService.getDesignationById(dto.getDesignationId());
        eventService.readSpecific(dto.getEventId());
        
        PointPoliceCount pointPoliceCount = new PointPoliceCount();
        pointPoliceCount.setDesignationCount(dto.getDesignationCount());
        pointPoliceCount.setEventId(dto.getEventId());
        pointPoliceCount.setDesignationId(dto.getDesignationId());
        pointPoliceCount.setPointId(dto.getPointId());
        
        if (isSave) {
            return pointPoliceCountRepository.save(pointPoliceCount);
        } else {
            return pointPoliceCount;
        }
    }


    public List<PointPoliceCount> saveMultiplePointPoliceCount(Map<String, Object> body) {
        List<PointPoliceCount> resp = new ArrayList<>();
        Event event = eventService.readSpecific(Long.valueOf(body.get("event-id").toString()));
        Point point = pointService.readSpecific(Long.valueOf(body.get("point-id").toString()));
        Map<String, String> designationCounts = (Map<String, String>) body.get("designations");

        // TODO:event and point validation check in pointPoliceCount, if exists then data already there please try update methood
        boolean isEventAndPointAllocationExists = pointPoliceCountRepository.checkEventAndPointAllocationExists(event.getId(), point.getId());

        // data already exists
        if (isEventAndPointAllocationExists){
            throw new DataSavingException("Data already exists for event " + event.getEventName() + " try updating the event's point allocation of point name " + point.getPointName());
        }

        for (Map.Entry<String, String> designationCount : designationCounts.entrySet()) {
            Long designationId = Long.parseLong(designationCount.getKey());
            Integer count = Integer.parseInt(designationCount.getValue());
            PointPoliceCountDto dto = new PointPoliceCountDto();
            dto.setEventId(event.getId());
            dto.setDesignationId(designationId);
            dto.setDesignationCount(count);
            dto.setPointId(point.getId());
            PointPoliceCount pointPoliceCount = savePointPoliceCountIndividual(dto, Boolean.FALSE);
            resp.add(pointPoliceCount);
        }
        return (List<PointPoliceCount>) CollectionUtil.makeCollection(pointPoliceCountRepository.saveAll(resp));
    }

    public List<PointPoliceCount> updateMultiplePointPoliceCount(Map<String, Object> body) {
        List<PointPoliceCount> resp = new ArrayList<>();
        Event event = eventService.readSpecific(Long.valueOf(body.get("event-id").toString()));
        Point point = pointService.readSpecific(Long.valueOf(body.get("point-id").toString()));
        Map<String, String> designationCounts = (Map<String, String>) body.get("designations");
        List<PointPoliceCount> pointPoliceCounts = pointPoliceCountRepository.getAllByEventAndPointId(event.getId(), point.getId());

        // if designation id exists then update, otherwise add.
        for (Map.Entry<String, String> designationCount : designationCounts.entrySet()){
            Long designationId = Long.parseLong(designationCount.getKey());
            Integer count = Integer.parseInt(designationCount.getValue());

            PointPoliceCountDto dto = new PointPoliceCountDto();
            dto.setEventId(event.getId());
            dto.setDesignationId(designationId);
            dto.setDesignationCount(count);
            dto.setPointId(point.getId());
            PointPoliceCount pointPoliceCount = savePointPoliceCountIndividual(dto, Boolean.FALSE);
            boolean isDesignationPreviouslyExists = false;

            for(PointPoliceCount oldPointPoliceCount : pointPoliceCounts){
                if(Objects.equals(oldPointPoliceCount.getDesignationId(), designationId)){
                    isDesignationPreviouslyExists = true;
                    oldPointPoliceCount.setDesignationCount(count);
                    resp.add(oldPointPoliceCount);
                }
            }
            if(!isDesignationPreviouslyExists){
                resp.add(pointPoliceCount);
            }
        }
        return (List<PointPoliceCount>) CollectionUtil.makeCollection(pointPoliceCountRepository.saveAll(resp));
    }

    public void deletePointPoliceCount(Long eventId, Long pointId) {
        List<PointPoliceCount> pointPoliceCountList = pointPoliceCountRepository.getAllByEventAndPointId(eventId, pointId);
        pointPoliceCountRepository.deleteAll(pointPoliceCountList);
    }

    public List<PointPoliceCount> readAllByEventAndPoint(Long eventId, Long pointId) {
        return null;
    }

    public EventPointPoliceCountAssignmentRespDto readAllByPointDesignationCountsAndEventName(Long eventId, Long pointId) {
        Event event = eventService.readSpecific(eventId);
        Point point = pointService.readSpecific(pointId);
        List<PointPoliceCount> data = pointPoliceCountRepository.getAllByEventAndPointId(eventId, pointId);
        List<DesignationDto> allDesignations = designationService.getAllDesignations();
//        if (data.size() == 0) {
//            throw new DataNotFoundException("No data found for event " + event.getEventName() + " with point " + point.getPointName());
//        }
        EventPointPoliceCountAssignmentRespDto resp = new EventPointPoliceCountAssignmentRespDto();
        resp.setEventId(eventId);
        resp.setPointId(pointId);
        List<DesignationCountRespDto> designationCountRespDtos = new ArrayList<>();
        allDesignations.stream().forEach(d -> {
            DesignationCountRespDto dto = new DesignationCountRespDto();

            if (CollectionUtil.nonNullNonEmpty(data)){
                PointPoliceCount ppc = data.stream().filter(p -> p.getDesignationId().equals(d.getId())).findAny().orElse(null);
                dto.setDesignationCount(ppc != null ? ppc.getDesignationCount() : 0);
            } else {
                // make designation count 0
                dto.setDesignationCount(0);
            }
            dto.setDesignationId(d.getId());
            dto.setDesignationName(d.getName());
            designationCountRespDtos.add(dto);
        });
//        data.forEach( pointPoliceCount -> {
//            DesignationCountRespDto dto = new DesignationCountRespDto();
//            dto.setDesignationId(pointPoliceCount.getDesignationId());
//            dto.setDesignationName(designationService.getDesignationById(pointPoliceCount.getDesignationId()).getName());
//            dto.setDesignationCount(pointPoliceCount.getDesignationCount());
//            designationCountRespDtos.add(dto);
//        });
        resp.setAssignments(designationCountRespDtos.stream().sorted(Comparator.comparing(DesignationCountRespDto::getDesignationId)).collect(Collectors.toList()));
        resp.setEventName(event.getEventName());
        resp.setPointName(point.getPointName());
        return resp;
//        return null;
    }

    public List<EventPoliceCountAssignmentRowDto> readAllInAssignmentFormat() {
        return null;
    }

    public List<EventPointPoliceCountAssignmentRespDto> getAllPointPoliceCountForEvent(Long eventId) {
        List<Long> pointsInEvent = pointPoliceCountRepository.getAllPointIdsForEvent(eventId);
        List<EventPointPoliceCountAssignmentRespDto> eventAssignmentList = new ArrayList<>();
        pointsInEvent.stream().forEach(point -> {
            EventPointPoliceCountAssignmentRespDto dto = readAllByPointDesignationCountsAndEventName(eventId, point);
            eventAssignmentList.add(dto);
        });
        return eventAssignmentList;
    }
}
