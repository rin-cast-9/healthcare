package com.rincast.healthcare_backend.controller;


import com.rincast.healthcare_backend.dto.HRVRequest;
import com.rincast.healthcare_backend.dto.HRVResponse;
import com.rincast.healthcare_backend.dto.HeartRateGetDataResponse;
import com.rincast.healthcare_backend.dto.HeartRateRequest;
import com.rincast.healthcare_backend.dto.HeartRateResponse;
import com.rincast.healthcare_backend.model.User;
import com.rincast.healthcare_backend.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/heart")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/heartrate")
    public ResponseEntity<HeartRateResponse> addHeartRate(
            @RequestBody HeartRateRequest heartRateRequest) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return ResponseEntity.ok(heartService.addHeartRate(userId, heartRateRequest.getHeartRate(), heartRateRequest.getTimestamp()));
    }

    @PostMapping("/hrv")
    public ResponseEntity<HRVResponse> addHeartRateVariability(
            @RequestBody HRVRequest hrvRequest) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return ResponseEntity.ok(heartService.addHeartRateVariability(userId, hrvRequest.getHrv(), hrvRequest.getTimestamp()));
    }

    @GetMapping("/getheartrate")
    public ResponseEntity<List<HeartRateGetDataResponse>> getHeartRateData() {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return ResponseEntity.ok(heartService.getHeartRateData(userId));
    }

}
