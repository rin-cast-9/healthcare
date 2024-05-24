package com.rincast.healthcare_backend.repository;

import com.rincast.healthcare_backend.model.DataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DataTypeRepository extends JpaRepository <DataType, Long> {
}
