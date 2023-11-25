package com.walther.microservices.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiResponse {
    @NonNull
    private String message;
    @NonNull
    private boolean success;
}
