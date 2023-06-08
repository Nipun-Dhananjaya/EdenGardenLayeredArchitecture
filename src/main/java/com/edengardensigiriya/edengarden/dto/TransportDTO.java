package com.edengardensigiriya.edengarden.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransportDTO {
    private String transId;
    private String custId;
    private String custName;
    private String dateTime;
    private String destination;
    private String cost;
    private String status;
    private String paymentId;
    public TransportDTO(String transId,String custId,String transDateTime,String destination,String paymentId,String transCost,boolean b){
        this.transId=transId;
        this.custId=custId;
        this.dateTime=transDateTime;
        this.destination=destination;
        this.paymentId=paymentId;
        this.cost=transCost;
    }
    public TransportDTO(String transId,String custId,String custName,String transDateTime,String destination,String transCost,String transStatus){
        this.transId=transId;
        this.custId=custId;
        this.custName=custName;
        this.dateTime=transDateTime;
        this.destination=destination;
        this.cost=transCost;
        this.status=transStatus;
    }
    public TransportDTO(String transId,String paymentId,String transStatus){
        this.transId=transId;
        this.paymentId=paymentId;
        this.status=transStatus;
    }
    public TransportDTO(String transId,String custId,String transDateTime,String paymentId,String destination,String transCost,int a){
        this.transId=transId;
        this.custId=custId;
        this.dateTime=transDateTime;
        this.paymentId=paymentId;
        this.destination=destination;
        this.cost=transCost;
    }
}
