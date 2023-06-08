package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.ChangePacksBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.QueryDAO;

import java.sql.SQLException;

public class ChangePacksBOImpl implements ChangePacksBO {
    QueryDAO queryDAO= (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public boolean changePackage(String packagePrice, String newPrice) throws SQLException {
        return queryDAO.changePakage(packagePrice,newPrice);
    }

    @Override
    public void setArrayList() throws SQLException {
        queryDAO.setArrayList();
    }

    @Override
    public String getSleepCount(String price) throws SQLException {
        return queryDAO.getSleepCount(price);
    }
}
