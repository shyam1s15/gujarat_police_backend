package com.shyam.gujarat_police.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointPoliceAssignmentRespDto {

    @JsonProperty("point-id")
    private Long pointId;

    @JsonProperty("point-name")
    private String pointName;

    @JsonProperty("zone-name")
    private String zoneName;

    @JsonProperty("assignment-count")
    private Integer assignmentCount;

    @JsonProperty("assigned-police-list")
    private List<PoliceInPointAndEventDto> assignedPoliceList;
}
