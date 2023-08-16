package com.example.SchoolInformation.utils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Meta {

    @JsonProperty("correlation_id")
    String correlationId;

    @JsonProperty("datetime")
    Date datetime;

}
