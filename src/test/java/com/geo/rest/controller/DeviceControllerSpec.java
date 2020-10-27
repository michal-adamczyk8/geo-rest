package com.geo.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geo.rest.model.Device;
import com.geo.rest.model.Localization;
import com.geo.rest.service.LocalizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class DeviceControllerSpec {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocalizationServiceImpl localizationServiceImpl;

    @Test
    public void shouldReturnStatusOkAndGoodResponse() throws Exception {
        //Given
        Device device = new Device();
        device.setDeviceId(1L);
        Localization localization = new Localization("1", "1", device);
        localization.setLocalizationId(1L);
        String requestContent = objectMapper.writeValueAsString(new LocalizationRequest("1", "1", "1"));
        when(localizationServiceImpl.saveLocalization(any(LocalizationRequest.class))).thenReturn(localization);
        //When && Then
        mockMvc.perform(post("/device")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$").exists())
               .andExpect(jsonPath("$.localizationId").value(1L))
               .andExpect(jsonPath("$.latitude").value(1L))
               .andExpect(jsonPath("$.longitude").value(1L));
    }

    @Test
    public void shouldReturnBadRequestWhenLatitudeInRequestIsNull() throws Exception {
        //Given
        Device device = new Device();
        device.setDeviceId(1L);
        Localization localization = new Localization("1", "1", device);
        localization.setLocalizationId(1L);
        String requestContent = objectMapper.writeValueAsString(new LocalizationRequest("1", null, "1"));
        when(localizationServiceImpl.saveLocalization(any(LocalizationRequest.class))).thenReturn(localization);
        //When && Then
        mockMvc.perform(post("/device")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenLongitudeInRequestIsNull() throws Exception {
        //Given
        Device device = new Device();
        device.setDeviceId(1L);

        Localization localization = new Localization("1", "1", device);
        localization.setLocalizationId(1L);

        String requestContent = objectMapper.writeValueAsString(new LocalizationRequest("1", "1", null));
        when(localizationServiceImpl.saveLocalization(any(LocalizationRequest.class))).thenReturn(localization);

        //When && Then
        mockMvc.perform(post("/device")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenDeviceIdInRequestIsNull() throws Exception {
        //Given
        Device device = new Device();
        device.setDeviceId(1L);

        Localization localization = new Localization("1", "1", device);
        localization.setLocalizationId(1L);

        String requestContent = objectMapper.writeValueAsString(new LocalizationRequest(null, "1", "1"));

        when(localizationServiceImpl.saveLocalization(any(LocalizationRequest.class))).thenReturn(localization);

        //When && Then
        mockMvc.perform(post("/device")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
