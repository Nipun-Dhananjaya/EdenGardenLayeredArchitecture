package com.edengardensigiriya.edengarden.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private String roomNo;
    private String roomType;
    private String sleepCount;
    private String costPerDay;
    private String availability;
}
