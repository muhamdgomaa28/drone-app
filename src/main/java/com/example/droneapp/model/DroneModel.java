package com.example.droneapp.model;

import com.example.droneapp.model.enums.ModelEnum;
import com.example.droneapp.model.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneModel implements Serializable  {
    private static final long serialVersionUID =1L;

    private Long id;

    @NotEmpty(message = "serial number cant be null")
    private String serialNumber;

    @NotNull(message = "model cant be null")
    private ModelEnum model;

    @NotNull(message = "weight cant be null")
    @Min(value = 0 , message = "battery cant be less than 0 grm")
    @Max(value = 500 , message = "battery cant be more than 500 grm")
    private double weightLimit;

    @NotNull(message = "battery cant be null")
    @Max(value = 100 , message = "battery cant be more than 100 %")
    private double batteryCapacityPercentage;
    @NotNull(message = "state cant be null")
    private StateEnum state;
}
