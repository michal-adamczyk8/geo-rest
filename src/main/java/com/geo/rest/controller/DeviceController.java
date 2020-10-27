package com.geo.rest.controller;

import com.geo.rest.model.Localization;
import com.geo.rest.service.LocalizationServiceImpl;
import com.geo.rest.service.exception.ProvidedDeviceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/device")
@Validated
public class DeviceController {

    static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    private final LocalizationServiceImpl localizationServiceImpl;

    public DeviceController(LocalizationServiceImpl localizationServiceImpl) {
        this.localizationServiceImpl = localizationServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Localization> saveDeviceGeoLocalization(@Valid @RequestBody LocalizationRequest localizationRequest)
            throws ProvidedDeviceNotFoundException {
        log.info("New request: " + localizationRequest.toString());
        Localization localization = localizationServiceImpl.saveLocalization(localizationRequest);
        return new ResponseEntity(localization, HttpStatus.OK);
    }
}
