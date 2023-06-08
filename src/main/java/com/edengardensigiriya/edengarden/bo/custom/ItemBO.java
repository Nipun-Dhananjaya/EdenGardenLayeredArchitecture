package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    List<ItemDTO> searchItems(String itemCode) throws SQLException, ClassNotFoundException;
    boolean saveItems(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    boolean updateItems(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    boolean removeItems(String itemCode) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;
}
