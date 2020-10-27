package com.geo.rest.controller;

import com.geo.rest.common.ErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LocalizationRequest {

    @NotBlank(message = ErrorMessages.DEVICE_ID_MISSING)
    private String deviceId;

    @NotBlank(message = ErrorMessages.LATITUDE_MISSING)
    private String latitude;

    @NotBlank(message = ErrorMessages.LONGITUDE_MISSING)
    private String longitude;
}
