package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.dto.request.PointDto;
import com.shyam.gujarat_police.entities.Point;
import com.shyam.gujarat_police.entities.Zone;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {
    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private ZoneService zoneService;

    public List<PointDto> getPoints() {
        List<Point> points = (List<Point>) pointRepository.findAll();
        return points.stream().map(point-> {
            PointDto dto = new PointDto();
            dto.setId(point.getId());
            dto.setPointName(point.getPointName());
            dto.setZone(point.getZone().getId());
            dto.setZoneName(point.getZone().getName());
            dto.setDistrict(point.getDistrict());
            dto.setTaluka(point.getTaluka());
            dto.setAccessories(point.getAccessories());
            dto.setRemarks(point.getRemarks());
            return dto;
        }).collect(Collectors.toList());
    }

    public Point savePoint(PointDto dto) {
        Point point = new Point();
        point.setPointName(dto.getPointName());
        point.setTaluka(dto.getTaluka());
        point.setDistrict(dto.getDistrict());
        point.setAccessories(dto.getAccessories());
        point.setRemarks(dto.getRemarks());
        Zone zone = zoneService.readSpecifiZone(dto.getZone());
        point.setZone(zone);
        return pointRepository.save(point);
    }

    public Point updatePoint(PointDto point, Long pointId) {
        Point obtainedPoint = pointRepository.findById(pointId)
                .orElseThrow(()->new DataNotFoundException("Point not found with PointId: " + pointId));
        Zone zone = zoneService.readSpecifiZone(point.getZone());

        obtainedPoint.setTaluka(point.getTaluka());
        obtainedPoint.setDistrict(point.getDistrict());
        obtainedPoint.setPointName(point.getPointName());
//        obtainedPoint.setAssignPolice(obtainedPoint.getAssignPolice());
        obtainedPoint.setAccessories(point.getAccessories());
        obtainedPoint.setRemarks(point.getRemarks());
        obtainedPoint.setZone(zone);
        pointRepository.save(obtainedPoint);
        return obtainedPoint;
    }

    public Point readSpecific(Long pointId) {
        return pointRepository.findById(pointId)
                .orElseThrow(()->new DataNotFoundException("Point with id: " + pointId + " not found"));
    }
    public void deletePoint(Long pointId) {
        pointRepository.deleteById(pointId);
    }

}
