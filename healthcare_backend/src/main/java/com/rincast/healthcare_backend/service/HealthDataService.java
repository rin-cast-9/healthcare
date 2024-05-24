package com.rincast.healthcare_backend.service;


import com.rincast.healthcare_backend.model.DataType;
import com.rincast.healthcare_backend.model.HealthData;
import com.rincast.healthcare_backend.model.SleepData;
import com.rincast.healthcare_backend.model.User;
import com.rincast.healthcare_backend.repository.DataTypeRepository;
import com.rincast.healthcare_backend.repository.HealthDataRepository;
import com.rincast.healthcare_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthDataService {

    private final HealthDataRepository healthDataRepository;

    private final UserRepository userRepository;

    private final DataTypeRepository dataTypeRepository;

    @Autowired
    public HealthDataService(
            HealthDataRepository healthDataRepository,
            UserRepository userRepository,
            DataTypeRepository dataTypeRepository) {
        this.healthDataRepository = healthDataRepository;
        this.userRepository = userRepository;
        this.dataTypeRepository = dataTypeRepository;
    }

    public void saveHealthData(
            Long userId,
            Long dataTypeId,
            Long integerValue,
            String unit,
            LocalDateTime timestamp) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        DataType dataType = dataTypeRepository.findById(dataTypeId).orElseThrow(() -> new RuntimeException("Data type not found"));

        System.out.println(dataType);

        HealthData healthData = HealthData.builder()
                .user(user)
                .dataType(dataType)
                .valueType(0)
                .integerValue(integerValue)
                .floatValue(null)
                .stringValue(null)
                .unit(unit)
                .timestamp(timestamp)
                .build();

        healthDataRepository.save(healthData);
    }

    public void saveHealthData(
            Long userId,
            Long dataTypeId,
            Double floatValue,
            String unit,
            LocalDateTime timestamp) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        DataType dataType = dataTypeRepository.findById(dataTypeId).orElseThrow(() -> new RuntimeException("Data type not found"));

        HealthData healthData = HealthData.builder()
                .user(user)
                .dataType(dataType)
                .valueType(0)
                .integerValue(null)
                .floatValue(floatValue)
                .stringValue(null)
                .unit(unit)
                .timestamp(timestamp)
                .build();

        healthDataRepository.save(healthData);
    }

    public void saveHealthData(
            Long userId,
            Long dataTypeId,
            SleepData stringValue,
            String unit,
            LocalDateTime timestamp) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        DataType dataType = dataTypeRepository.findById(dataTypeId).orElseThrow(() -> new RuntimeException("Data type not found"));

        HealthData healthData = HealthData.builder()
                .user(user)
                .dataType(dataType)
                .valueType(0)
                .integerValue(null)
                .floatValue(null)
                .stringValue(stringValue)
                .unit(unit)
                .timestamp(timestamp)
                .build();

        healthDataRepository.save(healthData);
    }

    public List<HealthData> getHealthDataByUserId(Long userId) {
        return healthDataRepository.findByUserId(userId);
    }

    public List<HealthData> getHealthDataByUserIdAndDataType(Long userId, Long dataTypeId) {
        return healthDataRepository.findByUserIdAndDataTypeId(userId, dataTypeId);
    }

    public void deleteHealthDataById(Long id) {
        healthDataRepository.deleteById(id);
    }

    public void deleteAllHealthDataByUserId(Long userId) {
        healthDataRepository.deleteAllByUserId(userId);
    }

    public void deleteBetweenDatesAndUserId(LocalDateTime from, LocalDateTime to, Long userId) {
        healthDataRepository.deleteByTimestampBetweenAndUserId(from, to, userId);
    }

}
