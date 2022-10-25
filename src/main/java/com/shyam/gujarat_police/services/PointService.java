package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.Point;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {
    @Autowired
    private PointRepository pointRepository;
    public List<Point> getPoints() {
        return (List<Point>) pointRepository.findAll();
    }

    public Point savePoint(Point point) {
        return pointRepository.save(point);
    }

    public Point updatePoint(Point point, Long pointId) {
        Point obtainedPoint = pointRepository.findById(pointId)
                .orElseThrow(()->new DataNotFoundException("Point not found with PointId: " + pointId));

        obtainedPoint.setTaluka(point.getTaluka());
        obtainedPoint.setDistrict(point.getDistrict());
        obtainedPoint.setPointName(point.getPointName());
        obtainedPoint.setAssignPolice(point.getAssignPolice());
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
