package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Custom,String> {
    public String setNextIdValue(int number);
    public String getEmail(String id) throws SQLException;
    public String getOrderId();
    public boolean cancelOrder(String ordId) throws SQLException;
    List<Custom> getAllItemsOfOrder(String ordId) throws SQLException;
}
