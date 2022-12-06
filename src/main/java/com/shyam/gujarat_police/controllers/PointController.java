package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.Point;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @GetMapping("/")
    public APIResponse getPoints() {
        return APIResponse.ok( pointService.getPoints() );
    }

    @PostMapping("/")
    public APIResponse savePoint(@RequestBody @Valid Point point) {
        Point dto = pointService.savePoint(point);
        return APIResponse.ok(dto);
    }

    @PutMapping("/{pointId}")
    public APIResponse updatePoint(@RequestBody @Valid Point point,
                             @PathVariable("pointId") Long pointId) {
        Point dto = pointService.updatePoint(point, pointId);
        return APIResponse.ok(dto);
    }

    @GetMapping("/{pointId}")
    public APIResponse readSpecific(@PathVariable("pointId") Long pointId) {
        Point dto = pointService.readSpecific(pointId);
        return APIResponse.ok(dto);
    }

    @DeleteMapping("/{pointId}")
    public APIResponse deleteSpecific(@PathVariable("pointId") Long pointId) {
        pointService.deletePoint(pointId);
        return APIResponse.ok("point deleted successfully with id " + pointId);
    }
}
