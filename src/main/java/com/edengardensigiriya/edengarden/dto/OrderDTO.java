package com.edengardensigiriya.edengarden.dto;

import com.edengardensigiriya.edengarden.entity.Custom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    private String ordId;
    private String suppId;
    private String items;
    private String qty;
    private String orderedDateTime;
    private String deliverDateTime;
    private String cost;
    private String status;
    private List<OrderItemDTO> orderItemDTOS;
    public OrderDTO(String ordId,String suppId,String itemDescription,String qty,String orderedDateTime,String deliverDateTime,String ordCost,String ordStatus){
        this.ordId=ordId;
        this.suppId=suppId;
        this.items=itemDescription;
        this.qty=qty;
        this.orderedDateTime=orderedDateTime;
        this.deliverDateTime=deliverDateTime;
        this.cost=ordCost;
        this.status=ordStatus;
    }
    public OrderDTO(String ordId, String suppId, List<OrderItemDTO> orderItem, String deliverDateTime, String ordCost){
        this.ordId=ordId;
        this.suppId=suppId;
        this.orderItemDTOS=orderItem;
        this.deliverDateTime=deliverDateTime;
        this.cost=ordCost;
    }
}
