package com.shyam.gujarat_police.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordsRespDto {

    @JsonProperty("event-name")
    private String eventName;

    @JsonProperty("created-at")
    private Date createdAt;

    @JsonProperty("valid-upto")
    private String validUpto;

    @JsonProperty("is-expired")
    private boolean isExpired;


}
