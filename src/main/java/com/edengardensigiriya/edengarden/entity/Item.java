package com.edengardensigiriya.edengarden.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Item {
    private String itemCode;
    private String itemDescription;

    public Item(String itemDescription) {
        this.itemDescription=itemDescription;
    }
}
