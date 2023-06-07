package com.edengardensigiriya.edengarden.dao;

import com.edengardensigiriya.edengarden.dao.custom.impl.*;

public class DAOFactory {
    private DAOFactory(){}
    private static DAOFactory daoFactory;

    public static DAOFactory getDaoFactory() {
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,QUERY,BICYCLE,CAR,EMPLOYER,ROOM,ITEM,SUPPLIER,PAYMENT,BOOKING,RENTAL,TRANSPORT,ORDER,USER
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
            case PAYMENT:return new PaymentDAOImpl();
            case BOOKING:return new BookingDAOImpl();
            case RENTAL:return new RentalDAOImpl();
            case TRANSPORT:return new TransportDAOImpl();
            case ORDER:return new OrderDAOImpl();
            case USER:return new UserDAOImpl();
            case QUERY:return (SuperDAO) new QueryDAOImpl();
            default: return null;
        }
    }
}
