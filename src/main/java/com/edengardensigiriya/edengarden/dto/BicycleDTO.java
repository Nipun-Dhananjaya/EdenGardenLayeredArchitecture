package com.edengardensigiriya.edengarden.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BicycleDTO {
    private String bicycleNo;
    private String brand;
    private String bicycleType;
    private String colour;
    private String status;
}
