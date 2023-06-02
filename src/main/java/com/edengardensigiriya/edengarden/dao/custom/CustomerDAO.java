package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Customer;

import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public ArrayList<String> contact = new ArrayList<>();
    public boolean addContact(String tele);
    public String setNextIdValue(int number);
    public String getCustId();
}
