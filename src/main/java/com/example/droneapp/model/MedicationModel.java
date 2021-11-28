package com.example.droneapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationModel {
    private Long id;

    private String name;
    private double weight;
    @NotEmpty(message = "code number cant be null")
    private String code;
    private String imageUrl;
}
