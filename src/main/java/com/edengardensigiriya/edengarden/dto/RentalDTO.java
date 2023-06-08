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
    private String vehicle;
    private String paymentId;

    public RentalDTO(String vehicleId){
        this.vehicleId=vehicleId;
    }
    public RentalDTO(String rentId,String custId,String vehicle,String vehicleId,String paymentId,String rentStatus){
        this.rentId=rentId;
        this.custId=custId;
        this.vehicle=vehicle;
        this.vehicleId=vehicleId;
        this.paymentId=paymentId;
        this.rentStatus=rentStatus;
    }
    public RentalDTO(String rentId,String custId,String custName,String vehicleType,String vehicleId,String rentFrom,String rentDuration,String rentCost,String rentStatus){
        this.rentId=rentId;
        this.custId=custId;
        this.custName=custName;
        this.vehicleType=vehicleType;
        this.vehicleId=vehicleId;
        this.rentFrom=rentFrom;
        this.rentDuration=rentDuration;
        this.rentCost=rentCost;
        this.rentStatus=rentStatus;
    }
    public RentalDTO(String rentId,String custId,String rentFrom,String rentDuration,String paymentId,String vehicle,String vehicleType,String vehicleId,String rentCost,boolean b){
        this.rentId=rentId;
        this.custId=custId;
        this.rentFrom=rentFrom;
        this.rentDuration=rentDuration;
        this.paymentId=paymentId;
        this.vehicle=vehicle;
        this.vehicleType=vehicleType;
        this.vehicleId=vehicleId;
        this.rentCost=rentCost;
    }
    public RentalDTO(String rentId,String custId,String rentFrom,String rentDuration,String paymentId,String rentCost,String vehicle,String vehicleType,String vehicleId,int a,boolean b){
        this.rentId=rentId;
        this.custId=custId;
        this.rentFrom=rentFrom;
        this.rentDuration=rentDuration;
        this.paymentId=paymentId;
        this.rentCost=rentCost;
        this.vehicle=vehicle;
        this.vehicleType=vehicleType;
        this.vehicleId=vehicleId;
    }
}
