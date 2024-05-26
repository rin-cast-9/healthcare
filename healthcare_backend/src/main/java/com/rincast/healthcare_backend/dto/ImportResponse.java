package com.rincast.healthcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportResponse {

    public Long dataEntriesReceived;
    public Long dataEntriesIgnored;
    public Set<String> ignoredTypes;
    public Long totalTimeElapsed;

}
