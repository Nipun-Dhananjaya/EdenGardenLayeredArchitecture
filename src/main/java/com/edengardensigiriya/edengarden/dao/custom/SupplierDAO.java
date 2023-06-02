package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier,String> {
    public ArrayList<String> contact = new ArrayList<>();
    public boolean addContact(String tele);
    public String setNextIdValue(int number);
    public String getSuppId() throws SQLException;
}
