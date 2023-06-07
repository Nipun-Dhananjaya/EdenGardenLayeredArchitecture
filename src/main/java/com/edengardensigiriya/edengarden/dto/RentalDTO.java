package com.edengardensigiriya.edengarden.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class RentalDTO {
    private String rentId;
    private String custId;
    private String custName;
    private String vehicleType;
    private String vehicleId;
    private String rentFrom;
    private String rentDuration;
    private String rentCost;
    private String rentStatus;

    public RentalDTO(String vehicleId){
        this.vehicleId=vehicleId;
    }
}
