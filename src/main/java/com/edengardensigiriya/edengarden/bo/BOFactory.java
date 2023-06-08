package com.edengardensigiriya.edengarden.bo;

import com.edengardensigiriya.edengarden.bo.custom.impl.*;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.SuperDAO;
import com.edengardensigiriya.edengarden.dao.custom.impl.*;

public class BOFactory {
    private BOFactory(){}
    private static BOFactory BoFactory;

    public static BOFactory getBoFactory() {
        return (BoFactory==null)?BoFactory=new BOFactory():BoFactory;
    }
    public enum BOTypes{
        CUSTOMER,BICYCLE,CAR,EMPLOYER,ROOM,ITEM,SUPPLIER,PAYMENT,BOOKING,RENTAL,TRANSPORT,ORDER,CHANGE_USER,CHANGE_PACKS,
        FORGOT_PASSWORD,LOGIN,HOME
    }
    public static SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:return new CustomerBOImpl();
            case BICYCLE:return new BicycleBOImpl();
            case CAR:return new CarBOImpl();
            case EMPLOYER:return new EmployerBOImpl();
            case ROOM:return new RoomBOImpl();
            case ITEM:return new ItemBOImpl();
            case SUPPLIER:return new SupplierBOImpl();
            case PAYMENT:return new PaymentBOImpl();
            case BOOKING:return new BookingBOImpl();
            case RENTAL:return new RentalBOImpl();
            case TRANSPORT:return new TransportBOImpl();
            case ORDER:return new OrderBOImpl();
            case CHANGE_USER:return new ChangeUserBOImpl();
            case CHANGE_PACKS:return new ChangePacksBOImpl();
            case FORGOT_PASSWORD:return new ForgotPasswordBOImpl();
            case LOGIN:return new LoginBOImpl();
            case HOME:return new HomeBOImpl();
            default: return null;
        }
    }
}
