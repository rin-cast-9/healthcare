package com.rincast.healthcare_backend.service;


import com.rincast.healthcare_backend.model.DataType;
import com.rincast.healthcare_backend.repository.DataTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DataTypeInitializationService {

    private final DataTypeRepository dataTypeRepository;

    @Autowired
    public DataTypeInitializationService (DataTypeRepository dataTypeRepository) {
        this.dataTypeRepository = dataTypeRepository;
    }

    @PostConstruct
    public void init() {
        if (dataTypeRepository.count() == 0) {
            List<DataType> dataTypes = Arrays.asList(
                    new DataType(0L, "Heart Rate"),
                    new DataType(1L, "Heart Rate Variability")
            );
            dataTypeRepository.saveAll(dataTypes);
        }
    }

}
