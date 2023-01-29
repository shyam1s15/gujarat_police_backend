package com.shyam.gujarat_police.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    @JsonProperty("event-id")
    private Long eventId;

    @JsonProperty("event-name")
    private String eventName;

    @JsonProperty("event-details")
    private String eventDetails;

    @JsonProperty("event-start-date")
    private String eventStartDate;

    @JsonProperty("event-end-date")
    private String eventEndDate;
}
