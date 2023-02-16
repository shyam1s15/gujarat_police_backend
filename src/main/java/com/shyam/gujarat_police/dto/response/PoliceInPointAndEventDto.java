package com.shyam.gujarat_police.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PoliceInPointAndEventDto {
    @JsonProperty("police-id")
    private Long policeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("duty-start-date")
    private Date dutyStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("duty-end-date")
    private Date dutyEndDate;

    @JsonProperty("police-name")
    private String policeName;

    @JsonProperty("police-station-name")
    private String policeStationName;

    @JsonProperty("buckle-number")
    private String buckleNumber;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("number")
    private String number;

    @JsonProperty("age")
    private String age;

    @JsonProperty("district")
    private String district;
}
