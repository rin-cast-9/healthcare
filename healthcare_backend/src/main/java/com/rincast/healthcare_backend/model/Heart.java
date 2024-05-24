package com.rincast.healthcare_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Component
public class Heart {

    private Long heartRate;
    private Double heartRateVariability;
    private LocalDateTime timestamp;

}
