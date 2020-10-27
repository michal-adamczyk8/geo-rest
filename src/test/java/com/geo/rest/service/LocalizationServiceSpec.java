package com.geo.rest.service;

import com.geo.rest.common.ErrorMessages;
import com.geo.rest.controller.LocalizationRequest;
import com.geo.rest.model.Device;
import com.geo.rest.model.Localization;
import com.geo.rest.repository.DeviceRepository;
import com.geo.rest.repository.LocalizationRepository;
import com.geo.rest.service.exception.ProvidedDeviceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LocalizationServiceSpec {

    @Autowired
    LocalizationService localizationService;

    @MockBean
    LocalizationRepository localizationRepository;

    @MockBean
    DeviceRepository deviceRepository;

    @Test
    public void shouldSaveLocalization() throws ProvidedDeviceNotFoundException {
        //Given
        Device device = new Device();
        device.setDeviceId(1L);
        Localization localization = new Localization("1", "1", device);
        localization.setLocalizationId(1L);

        LocalizationRequest localizationRequest = new LocalizationRequest("1", "1", "1");

        when(deviceRepository.getByDeviceId(anyLong())).thenReturn(device);
        when(localizationRepository.save(any(Localization.class))).thenReturn(localization);

        //When
        Localization returnedLocalization = localizationService.saveLocalization(localizationRequest);
        //Then
        assertSame(returnedLocalization, localization);
    }

    @Test
    public void shouldThrowProvidedDeviceNotFound() {
        //Given
        Device device = new Device();
        device.setDeviceId(100L);
        Localization localization = new Localization("1", "1", device);
        localization.setLocalizationId(1L);

        LocalizationRequest localizationRequest = new LocalizationRequest("1", "1", "1");

        when(deviceRepository.getByDeviceId(anyLong())).thenReturn(null);
        when(localizationRepository.save(any(Localization.class))).thenReturn(localization);

        //When
        Exception exception = assertThrows(ProvidedDeviceNotFoundException.class, () -> localizationService.saveLocalization(localizationRequest));

        //Then
        assertEquals(ErrorMessages.DEVICE_ID_NOT_FOUND, exception.getMessage());
    }
}
