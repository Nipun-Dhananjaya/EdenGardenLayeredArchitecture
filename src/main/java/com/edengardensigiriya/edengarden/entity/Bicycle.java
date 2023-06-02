package com.edengardensigiriya.edengarden.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Bicycle {
    private String bicycleNo;
    private String brand;
    private String bicycleType;
    private String colour;
    private String status;
}
