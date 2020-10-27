package com.geo.rest.service;

import com.geo.rest.controller.DeviceController;
import com.geo.rest.controller.LocalizationRequest;
import com.geo.rest.model.Device;
import com.geo.rest.model.Localization;
import com.geo.rest.repository.DeviceRepository;
import com.geo.rest.repository.LocalizationRepository;
import com.geo.rest.service.exception.ProvidedDeviceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {
    static final Logger log = LoggerFactory.getLogger(LocalizationServiceImpl.class);

    private final LocalizationRepository localizationRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public Localization saveLocalization(LocalizationRequest localizationRequest) throws ProvidedDeviceNotFoundException {
        Long deviceId = Long.valueOf(localizationRequest.getDeviceId());
        log.info("Looking for device with id: " + deviceId);
        Device device = deviceRepository.getByDeviceId(deviceId);
        if (Objects.nonNull(device)) {
            log.info("The device with id: " + deviceId + " was found. Saving the localization...");
            return localizationRepository.save(new Localization(localizationRequest.getLatitude(),
                                                                localizationRequest.getLongitude(),
                                                                device));
        }
        log.error("Unable to find device with id: " + deviceId);
        throw new ProvidedDeviceNotFoundException();
    }
}
