package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;

import java.sql.SQLException;

public interface ChangePacksBO extends SuperBO {
    boolean changePackage(String packagePrice,String newPrice) throws SQLException;
    void setArrayList() throws SQLException;
    String getSleepCount(String price) throws SQLException;
}
