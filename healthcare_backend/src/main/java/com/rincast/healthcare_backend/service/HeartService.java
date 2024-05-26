package com.rincast.healthcare_backend.service;


import com.rincast.healthcare_backend.dto.HRVResponse;
import com.rincast.healthcare_backend.dto.HeartRateGetDataResponse;
import com.rincast.healthcare_backend.dto.HeartRateResponse;
import com.rincast.healthcare_backend.model.HealthData;
import com.rincast.healthcare_backend.repository.DataTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeartService {

    private final HealthDataService healthDataService;

    private final DataTypeRepository dataTypeRepository;

    @Autowired
    public HeartService(HealthDataService healthDataService, DataTypeRepository dataTypeRepository) {
        this.healthDataService = healthDataService;
        this.dataTypeRepository = dataTypeRepository;
    }

    public HeartRateResponse addHeartRate(Long userId, Long heartRateValue, LocalDateTime timestamp) {
        Long dataTypeId = 0L;
        String unit = "count/min";

        healthDataService.saveHealthData(userId, dataTypeId, heartRateValue, unit, timestamp, timestamp);

        return HeartRateResponse.builder()
                .userId(userId)
                .dataType(dataTypeRepository.findById(dataTypeId).toString())
                .heartRateValue(heartRateValue)
                .unit(unit)
                .timestamp(timestamp)
                .build();
    }

    public HRVResponse addHeartRateVariability(Long userId, Double heartRateVariabilityValue, LocalDateTime timestamp) {
        Long dataTypeId = 1L;
        String unit = "ms";

        healthDataService.saveHealthData(userId, dataTypeId, heartRateVariabilityValue, unit, timestamp, timestamp);

        return HRVResponse.builder()
                .userId(userId)
                .dataType(dataTypeRepository.findById(dataTypeId).toString())
                .hrvValue(heartRateVariabilityValue)
                .unit(unit)
                .timestamp(timestamp)
                .build();
    }

    public void deleteHeartDataById(Long id) {
        healthDataService.deleteHealthDataById(id);
    }

    public List<HeartRateGetDataResponse> getHeartRateData(Long userId) {
        var rawData = healthDataService.getHealthDataByUserIdAndDataType(userId, 0L);

        System.out.println(rawData.size());

        return rawData.stream()
                .map(healthData -> HeartRateGetDataResponse.builder()
                    .value(healthData.getIntegerValue())
                    .timestamp(healthData.getStartDate())
                    .build())
                .toList();
    }

}
