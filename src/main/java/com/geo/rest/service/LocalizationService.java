package com.geo.rest.service;

import com.geo.rest.controller.LocalizationRequest;
import com.geo.rest.model.Localization;
import com.geo.rest.service.exception.ProvidedDeviceNotFoundException;

public interface LocalizationService {
    Localization saveLocalization(LocalizationRequest localizationRequest) throws ProvidedDeviceNotFoundException;
}
