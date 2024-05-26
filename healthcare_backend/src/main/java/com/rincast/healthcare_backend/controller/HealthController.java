package com.rincast.healthcare_backend.controller;


import com.rincast.healthcare_backend.dto.ImportRequest;
import com.rincast.healthcare_backend.dto.ImportResponse;
import com.rincast.healthcare_backend.model.User;
import com.rincast.healthcare_backend.service.HealthDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class HealthController {

    private final HealthDataService healthDataService;

    @PostMapping("/import")
    public ResponseEntity<ImportResponse> importData(
            @RequestBody ImportRequest importRequest) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return ResponseEntity.ok(healthDataService.importHealthData(userId, importRequest.getData()));
    }

}
