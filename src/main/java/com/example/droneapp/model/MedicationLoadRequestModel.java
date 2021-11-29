package com.example.droneapp.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MedicationLoadRequestModel {

    @NotNull(message = "medication items cant be null")
    private List<String> medicationCodes;

    @NotEmpty(message = "serial number cant be null")
    private String serialNumber;
}
