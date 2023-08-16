package com.example.SchoolInformation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Data {

    @JsonProperty("error_code")
    String statusCode;

    @JsonProperty("error_message")
    String message;

    @JsonProperty("description")
    String description;

    @JsonProperty("info")
    Object info;
}
