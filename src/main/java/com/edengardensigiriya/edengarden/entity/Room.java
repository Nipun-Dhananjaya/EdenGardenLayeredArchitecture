package com.edengardensigiriya.edengarden.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private String roomNo;
    private String roomType;
    private String sleepCount;
    private String costPerDay;
    private String availability;

    public Room(String roomNo, String roomType, String sleepCount, String costPerDay) {
        this.roomNo=roomNo;
        this.roomType=roomType;
        this.sleepCount=sleepCount;
        this.costPerDay=costPerDay;
    }
}
