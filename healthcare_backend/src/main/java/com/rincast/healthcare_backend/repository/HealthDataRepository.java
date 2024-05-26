package com.rincast.healthcare_backend.repository;

import com.rincast.healthcare_backend.model.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface HealthDataRepository extends JpaRepository<HealthData, Long> {
    List<HealthData> findByUserId(Long userId);

    List<HealthData> findByUserIdAndDataTypeId(Long userId, Long dataTypeId);

    void deleteAllByUserId(Long userId);

    void deleteByStartDateBetweenAndUserId(LocalDateTime from, LocalDateTime to, Long userId);

    List<HealthData> findByUserIdAndDataTypeIdAndStartDateIsAfter(Long userId, Long dataTypeId, LocalDateTime localDateTime);
}
