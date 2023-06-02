package com.edengardensigiriya.edengarden.dao;

import com.edengardensigiriya.edengarden.dao.custom.impl.*;

public class DAOFactory {
    private DAOFactory(){}
    private static DAOFactory daoFactory;

    public static DAOFactory getDaoFactory() {
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,QUERY,BICYCLE,CAR,EMPLOYER,ROOM,ITEM,SUPPLIER
    }
    public static SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:return new CustomerDAOImpl();
            case BICYCLE:return new BicycleDAOImpl();
            case CAR:return new CarDAOImpl();
            case EMPLOYER:return new EmployerDAOImpl();
            case ROOM:return new RoomDAOImpl();
            case ITEM:return new ItemDAOImpl();
            case SUPPLIER:return new SupplierDAOImpl();

            default: return null;
        }
    }
}
