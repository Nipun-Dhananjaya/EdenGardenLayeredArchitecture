package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {
    public List<Item> getAllDescription() throws SQLException;
    public String setNextIdValue(int number) throws SQLException;
}
