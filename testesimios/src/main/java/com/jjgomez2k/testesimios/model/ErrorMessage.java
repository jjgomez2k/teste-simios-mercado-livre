package com.jjgomez2k.testesimios.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorMessage {

    private LocalDateTime timeStamp;
    private String message;
    private String statusCode;
}
