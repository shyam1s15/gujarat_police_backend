package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.Point;
import com.shyam.gujarat_police.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @GetMapping("/")
    public List<Point> getPoints() {
        return pointService.getPoints();
    }

    @PostMapping("/")
    public Point savePoint(@RequestBody @Valid Point point) {
        return pointService.savePoint(point);
    }

    @PutMapping("/{pointId}")
    public Point updatePoint(@RequestBody @Valid Point point,
                             @PathVariable("pointId") Long pointId) {
        return pointService.updatePoint(point, pointId);
    }

    @GetMapping("/{pointId}")
    public Point readSpecific(@PathVariable("pointId") Long pointId) {
        return pointService.readSpecific(pointId);
    }

}
