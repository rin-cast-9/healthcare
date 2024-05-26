package com.rincast.healthcare_backend.service;


import com.rincast.healthcare_backend.dto.ImportResponse;
import com.rincast.healthcare_backend.model.DataType;
import com.rincast.healthcare_backend.model.HealthData;
import com.rincast.healthcare_backend.model.SleepData;
import com.rincast.healthcare_backend.model.User;
import com.rincast.healthcare_backend.repository.DataTypeRepository;
import com.rincast.healthcare_backend.repository.HealthDataRepository;
import com.rincast.healthcare_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
            LocalDateTime startDate,
            LocalDateTime endDate) {
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
                .startDate(startDate)
                .endDate(endDate)
                .build();

        healthDataRepository.save(healthData);
    }

    public void saveHealthData(
            Long userId,
            Long dataTypeId,
            Double floatValue,
            String unit,
            LocalDateTime startDate,
            LocalDateTime endDate) {
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
                .startDate(startDate)
                .endDate(endDate)
                .build();

        healthDataRepository.save(healthData);
    }

    public void saveHealthData(
            Long userId,
            Long dataTypeId,
            String stringValue,
            String unit,
            LocalDateTime startDate,
            LocalDateTime endDate) {
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
                .startDate(startDate)
                .endDate(endDate)
                .build();

        healthDataRepository.save(healthData);
    }

    public List<HealthData> getHealthDataByUserId(Long userId) {
        return healthDataRepository.findByUserId(userId);
    }

    public List<HealthData> getHealthDataByUserIdAndDataType(Long userId, Long dataTypeId) {
        return healthDataRepository.findByUserIdAndDataTypeId(userId, dataTypeId);
    }

    public List<HealthData> getHealthDataByUserIdAndDataTypeAndStartDateIsAfter(Long userId, Long dataTypeId, LocalDateTime localDateTime) {
        return healthDataRepository.findByUserIdAndDataTypeIdAndStartDateIsAfter(userId, dataTypeId, localDateTime);
    }

    public void deleteHealthDataById(Long id) {
        healthDataRepository.deleteById(id);
    }

    public void deleteAllHealthDataByUserId(Long userId) {
        healthDataRepository.deleteAllByUserId(userId);
    }

    public void deleteBetweenDatesAndUserId(LocalDateTime from, LocalDateTime to, Long userId) {
        healthDataRepository.deleteByStartDateBetweenAndUserId(from, to, userId);
    }

    @Transactional
    public ImportResponse importHealthData(Long userId, List<List<String>> data) {

        Long dataEntriesReceived = 0L;
        Long dataEntriesIgnored = 0L;
        Set<String> ignoredTypes = new HashSet<>();

        var availableTypes = getAvailableTypes();

        Long startTime = System.currentTimeMillis();

        try {
            for (var entry : data) {
                Long dataTypeId = isAvailableType(availableTypes, entry.get(0));

                if (dataTypeId == null) {
                    ++ dataEntriesIgnored;
                    ignoredTypes.add(entry.get(0));
                    continue;
                }

                Integer valueType = Integer.parseInt(entry.get(1));
                Long integerValue = null;
                Double floatValue = null;
                String stringValue = null;

                if (valueType == 0) {
                    integerValue = Long.parseLong(entry.get(2));
                }
                else if (valueType == 1) {
                    floatValue = Double.parseDouble(entry.get(3));
                }
                else if (valueType == 2) {
                    stringValue = entry.get(4);
                }

                String unit = entry.get(5);

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss X");

                LocalDateTime startDate = LocalDateTime.parse(entry.get(6), dateTimeFormatter);

                LocalDateTime endDate = LocalDateTime.parse(entry.get(7), dateTimeFormatter);

                User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                DataType dataType = dataTypeRepository.findById(dataTypeId).orElseThrow(() -> new RuntimeException("Data type not found"));

                healthDataRepository.save(HealthData.builder()
                        .user(user)
                        .dataType(dataType)
                        .valueType(valueType)
                        .integerValue(integerValue)
                        .floatValue(floatValue)
                        .stringValue(stringValue)
                        .unit(unit)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build());

                ++ dataEntriesReceived;
            }
        }
        catch (Exception e) {
            throw e;
        }

        Long endTime = System.currentTimeMillis();
        Long totalTimeElapsed = endTime - startTime;

        return ImportResponse.builder()
                .dataEntriesReceived(dataEntriesReceived)
                .dataEntriesIgnored(dataEntriesIgnored)
                .ignoredTypes(ignoredTypes)
                .totalTimeElapsed(totalTimeElapsed)
                .build();
    }

    private Map<String, Long> getAvailableTypes() {
        return dataTypeRepository.findAll().stream()
                .collect(Collectors.toMap(DataType::getName, DataType::getId));
    }

    private Long isAvailableType(Map<String, Long> availableTypes, String type) {
        if (availableTypes.containsKey(type)) {
            return availableTypes.get(type);
        }

        return null;
    }

}
