package com.edengardensigiriya.edengarden.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookingDTO {
    String bookingId;
    String paymentId;
    String custId;
    String custName;
    String roomNo;
    String bookFrom;
    String duration;
    String cost;
    String bookedOn;
    String availability;
    String paymentStatus;
    public BookingDTO(String bookingId, String custId, String bookFrom,String duration, String paymentId, String roomNo, String roomBookingCost, String bookedOn, String roomAvailability,String paymentStatus){
        this.bookingId=bookingId;
        this.custId=custId;
        this.bookFrom=bookFrom;
        this.duration=duration;
        this.paymentId=paymentId;
        this.roomNo=roomNo;
        this.cost=roomBookingCost;
        this.bookedOn=bookedOn;
        this.availability=roomAvailability;
        this.paymentStatus=paymentStatus;
    }
    public BookingDTO(String bookingId,String custId,String custName,String roomNo,String bookFrom,String duration,String roomBookingCost,String bookedOn,String roomAvailability){
        this.bookingId=bookingId;
        this.custId=custId;
        this.custName=custName;
        this.roomNo=roomNo;
        this.bookFrom=bookFrom;
        this.duration=duration;
        this.cost=roomBookingCost;
        this.bookedOn=bookedOn;
        this.availability=roomAvailability;
    }
    public BookingDTO(String bookingId, String paymentId,String paymentStatus, String roomNo){
        this.bookingId=bookingId;
        this.paymentId=paymentId;
        this.paymentStatus=paymentStatus;
        this.roomNo=roomNo;
    }
    public BookingDTO(String bookingId, String bookFrom,String duration, String paymentId, String roomNo, String roomBookingCost, String roomAvailability,String paymentStatus){
        this.bookingId=bookingId;
        this.bookFrom=bookFrom;
        this.duration=duration;
        this.paymentId=paymentId;
        this.roomNo=roomNo;
        this.cost=roomBookingCost;
        this.availability=roomAvailability;
        this.paymentStatus=paymentStatus;
    }
}
