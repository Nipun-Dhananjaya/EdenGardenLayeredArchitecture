package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.HomeBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.QueryDAO;

import java.sql.SQLException;

public class HomeBOImpl implements HomeBO {
    QueryDAO queryDAO= (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    @Override
    public Integer getBookingCount() throws SQLException {
        return queryDAO.getBookingCount();
    }

    @Override
    public Integer getCarRentCount() throws SQLException {
        return queryDAO.getCarRentCount();
    }

    @Override
    public Integer getBicycleCount() throws SQLException {
        return queryDAO.getBicycleCount();
    }

    @Override
    public Integer getTransCount() throws SQLException {
        return queryDAO.getTransCount();
    }

    @Override
    public Integer getMonthBookingCount() throws SQLException {
        return queryDAO.getMonthBookingCount();
    }

    @Override
    public Integer getMonthCarRentCount() throws SQLException {
        return queryDAO.getMonthCarRentCount();
    }

    @Override
    public Integer getMonthBicycleRentCount() throws SQLException {
        return queryDAO.getMonthBicycleRentCount();
    }

    @Override
    public Integer getMonthTransCount() throws SQLException {
        return queryDAO.getMonthTransCount();
    }
}
