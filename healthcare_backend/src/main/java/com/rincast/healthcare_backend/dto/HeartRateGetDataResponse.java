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
public class HeartRateGetDataResponse {

    public Long value;
    public LocalDateTime timestamp;

}
