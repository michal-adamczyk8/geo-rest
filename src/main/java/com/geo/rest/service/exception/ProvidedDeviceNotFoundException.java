package com.geo.rest.service.exception;

import com.geo.rest.common.ErrorMessages;

public class ProvidedDeviceNotFoundException extends Exception {
    public ProvidedDeviceNotFoundException() {
        super(ErrorMessages.DEVICE_ID_NOT_FOUND);
    }
}
