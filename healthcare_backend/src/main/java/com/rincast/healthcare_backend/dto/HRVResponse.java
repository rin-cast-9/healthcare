package com.rincast.healthcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HRVResponse {

    public Long userId;
    public String dataType;
    public Double hrvValue;
    public String unit;
    public LocalDateTime timestamp;

}
