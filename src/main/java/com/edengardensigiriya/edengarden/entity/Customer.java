package com.edengardensigiriya.edengarden.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    private String custId;
    private String custName;
    private String custNic;
    private String custEmail;
    private String custAddress;
    private String custContact;
    private String custGender;
}
