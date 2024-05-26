package com.rincast.healthcare_backend.controller;


import com.rincast.healthcare_backend.dto.DataTypeResponse;
import com.rincast.healthcare_backend.model.DataType;
import com.rincast.healthcare_backend.repository.DataTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/datatype")
@RequiredArgsConstructor
public class DataTypeController {

    private final DataTypeRepository dataTypeRepository;

    @GetMapping("/types")
    public ResponseEntity<DataTypeResponse> getDataTypes() {
        return ResponseEntity.ok(DataTypeResponse.builder()
                .types(dataTypeRepository.findAll()
                        .stream()
                        .map(DataType::getName)
                        .collect(Collectors.toList()))
                .build());
    }

}
