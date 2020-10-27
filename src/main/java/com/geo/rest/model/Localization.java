package com.geo.rest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Localization implements Serializable {

    public Localization(String latitude, String longitude, Device device) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.device = device;
        createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "localization_id")
    private Long localizationId;

    private String latitude;

    private String longitude;

    @ManyToOne
    @JoinColumn(name = "device_id")
    @JsonBackReference
    private Device device;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
