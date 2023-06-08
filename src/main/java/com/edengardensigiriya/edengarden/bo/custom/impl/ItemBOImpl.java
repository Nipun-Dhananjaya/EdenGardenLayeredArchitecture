package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.ItemBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.ItemDAO;
import com.edengardensigiriya.edengarden.dto.ItemDTO;
import com.edengardensigiriya.edengarden.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> itmList = new ArrayList<>();
        for (Item itm : itemDAO.getAll()) {
            itmList.add(new ItemDTO(
                    itm.getItemCode(),
                    itm.getItemDescription()
            ));
        }
        return itmList;
    }

    @Override
    public List<ItemDTO> searchItems(String itemCode) throws SQLException, ClassNotFoundException {
        List<ItemDTO> itemList = new ArrayList<>();
        for (Item itm : itemDAO.search(itemCode)) {
            itemList.add(new ItemDTO(
                    itm.getItemCode(),
                    itm.getItemDescription()
            ));
        }
        return null;
    }

    @Override
    public boolean saveItems(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemDTO.getItemCode(), itemDTO.getItemDescription()));
    }

    @Override
    public boolean updateItems(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itemDTO.getItemCode(), itemDTO.getItemDescription()));
    }

    @Override
    public boolean removeItems(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemCode);
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return itemDAO.newIdGenerate();
    }
}
