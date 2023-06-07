package com.edengardensigiriya.edengarden.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class Custom {
    private String bicycleNo;
    private String bicycleBrand;
    private String bicycleType;
    private String bicycleColour;
    private String bicycleStatus;

    private String bookingId;
    private String bookFrom;
    private String duration;
    private String roomBookingCost;
    private String bookedOn;
    private String roomAvailability;

    private String regNo;
    private String carBrand;
    private String carType;
    private String carColour;
    private String carStatus;

    private String custId;
    private String custName;
    private String custNic;
    private String custEmail;
    private String custAddress;
    private String custContact;
    private String custGender;

    private String empId;
    private String empName;
    private String empNic;
    private String empAddress;
    private String empEmail;
    private String empContact;
    private String empDob;
    private String empGender;
    private String jobRole;
    private String monthlySalary;
    private String enteredDate;
    private String resignedDate;

    private String itemCode;
    private String itemDescription;

    private String ordId;
    private String qty;
    private String orderedDateTime;
    private String deliverDateTime;
    private String ordCost;
    private String ordStatus;
    private List<Custom> orderItem;

    private String paymentId;
    private String paidDateTime;
    private String paidReason;
    private String payment;
    private String paymentStatus;

    private String rentId;
    private String vehicleType;
    private String vehicleId;
    private String rentFrom;
    private String rentDuration;
    private String rentCost;
    private String rentStatus;
    private String vehicle;

    private String roomNo;
    private String roomType;
    private String sleepCount;
    private String roomCostPerDay;
    private String availability;

    private String suppId;
    private String suppName;
    private String suppAddress;
    private String suppEmail;
    private String suppContact;
    private String ItemType;
    private String contactStartDate;
    private String contactEndDate;

    private String transId;
    private String transDateTime;
    private String destination;
    private String transCost;
    private String transStatus;

    //extra values used for override constructors
    //Booking
    public Custom(String bookingId,String custId,String custName,String roomNo,String bookFrom,String duration,String roomBookingCost,String bookedOn,String roomAvailability){
        this.bookingId=bookingId;
        this.custId=custId;
        this.custName=custName;
        this.roomNo=roomNo;
        this.bookFrom=bookFrom;
        this.duration=duration;
        this.roomBookingCost=roomBookingCost;
        this.bookedOn=bookedOn;
        this.roomAvailability=roomAvailability;
    }
    //BookingSave
    public Custom(String bookingId, String custId, String bookFrom,String duration, String paymentId, String roomNo, String roomBookingCost, String bookedOn, String roomAvailability,String paymentStatus){
        this.bookingId=bookingId;
        this.custId=custId;
        this.bookFrom=bookFrom;
        this.duration=duration;
        this.paymentId=paymentId;
        this.roomNo=roomNo;
        this.roomBookingCost=roomBookingCost;
        this.bookedOn=bookedOn;
        this.roomAvailability=roomAvailability;
        this.paymentStatus=paymentStatus;
    }
    //BookingUp
    public Custom(String bookingId, String bookFrom,String duration, String paymentId, String roomNo, String roomBookingCost, String roomAvailability,String paymentStatus,int an){
        this.bookingId=bookingId;
        this.bookFrom=bookFrom;
        this.duration=duration;
        this.paymentId=paymentId;
        this.roomNo=roomNo;
        this.roomBookingCost=roomBookingCost;
        this.roomAvailability=roomAvailability;
        this.paymentStatus=paymentStatus;
    }
    //BookingCancel
    public Custom(String bookingId, String paymentId,String paymentStatus, String roomNo,int no){
        this.bookingId=bookingId;
        this.paymentId=paymentId;
        this.paymentStatus=paymentStatus;
        this.roomNo=roomNo;
    }
    //BookingUI
    public Custom(String bookingId,String custId,String custName,String roomType,String roomNo,String sleepCount,String bookFrom,String duration,String roomBookingCost,int num){
        this.bookingId=bookingId;
        this.custId=custId;
        this.custName=custName;
        this.roomType=roomType;
        this.roomNo=roomNo;
        this.sleepCount=sleepCount;
        this.bookFrom=bookFrom;
        this.duration=duration;
        this.roomBookingCost=roomBookingCost;
    }
    //Order
    public Custom(String ordId,String suppId,String itemDescription,String qty,String orderedDateTime,String deliverDateTime,String ordCost,String ordStatus){
        this.ordId=ordId;
        this.suppId=suppId;
        this.itemDescription=itemDescription;
        this.qty=qty;
        this.orderedDateTime=orderedDateTime;
        this.deliverDateTime=deliverDateTime;
        this.ordCost=ordCost;
        this.ordStatus=ordStatus;
    }
    //OrderSave
    public Custom(String ordId, String suppId, List<Custom> orderItem, String deliverDateTime, String ordCost){
        this.ordId=ordId;
        this.suppId=suppId;
        this.orderItem=orderItem;
        this.deliverDateTime=deliverDateTime;
        this.ordCost=ordCost;
    }
    //OrderItem
    public Custom(String itemDescription,String qty){
        this.itemDescription=itemDescription;
        this.qty=qty;
    }
    //OrderUpdate
    public Custom(String ordId,String suppId,String deliverDateTime,String ordCost){
        this.ordId=ordId;
        this.suppId=suppId;
        this.deliverDateTime=deliverDateTime;
        this.ordCost=ordCost;
    }
    //Payment
    public Custom(String paymentId,String custId,String paidDateTime,String paidReason,String payment,String paymentStatus){
        this.paymentId=paymentId;
        this.custId=custId;
        this.paidDateTime=paidDateTime;
        this.paidReason=paidReason;
        this.payment=payment;
        this.paymentStatus=paymentStatus;
    }
    //Rental
    public Custom(String rentId,String custId,String custName,String vehicleType,String vehicleId,String rentFrom,String rentDuration,String rentCost,String rentStatus,float fl){
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
    //RentalSave
    public Custom(String rentId,String custId,String rentFrom,String rentDuration,String paymentId,String rentCost,String vehicle,String vehicleType,String vehicleId,int a,boolean b){
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
    //RentalUI
    public Custom(String rentId,String custId,String custName,String vehicle,String vehicleType,String vehicleId,String rentFrom,String rentDuration,String rentCost,char a){
        this.rentId=rentId;
        this.custId=custId;
        this.custName=custName;
        this.vehicle=vehicle;
        this.vehicleType=vehicleType;
        this.vehicleId=vehicleId;
        this.rentFrom=rentFrom;
        this.rentDuration=rentDuration;
        this.rentCost=rentCost;
    }
    //RentalUp
    public Custom(String rentId,String custId,String rentFrom,String rentDuration,String paymentId,String vehicle,String vehicleType,String vehicleId,String rentCost,boolean b){
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
    //RentalCancel
    public Custom(String rentId,String custId,String vehicle,String vehicleId,String paymentId,String rentStatus,float a){
        this.rentId=rentId;
        this.custId=custId;
        this.vehicle=vehicle;
        this.vehicleId=vehicleId;
        this.paymentId=paymentId;
        this.rentStatus=rentStatus;
    }
    //RentVehicleId
    public Custom(String vehicleId){
        this.vehicleId=vehicleId;
    }
    //Transport
    public Custom(String transId,String custId,String custName,String transDateTime,String destination,String transCost,String transStatus){
        this.transId=transId;
        this.custId=custId;
        this.custName=custName;
        this.transDateTime=transDateTime;
        this.destination=destination;
        this.transCost=transCost;
        this.transStatus=transStatus;
    }
    //TransportUI
    public Custom(String transId,String custId,String custName,String transDateTime,String destination,String transCost,char a){
        this.transId=transId;
        this.custId=custId;
        this.custName=custName;
        this.transDateTime=transDateTime;
        this.destination=destination;
        this.transCost=transCost;
    }
    //TransportUp
    public Custom(String transId,String custId,String transDateTime,String paymentId,String destination,String transCost,int a){
        this.transId=transId;
        this.custId=custId;
        this.transDateTime=transDateTime;
        this.paymentId=paymentId;
        this.destination=destination;
        this.transCost=transCost;
    }
    //TransportSave
    public Custom(String transId,String custId,String transDateTime,String destination,String paymentId,String transCost,boolean b){
        this.transId=transId;
        this.custId=custId;
        this.transDateTime=transDateTime;
        this.destination=destination;
        this.paymentId=paymentId;
        this.transCost=transCost;
    }
    //TransportCancel
    public Custom(String transId,String paymentId,String transStatus){
        this.transId=transId;
        this.paymentId=paymentId;
        this.transStatus=transStatus;
    }
}
