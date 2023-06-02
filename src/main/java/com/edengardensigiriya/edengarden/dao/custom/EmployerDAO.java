package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Employer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployerDAO extends CrudDAO<Employer,String> {
    public static ArrayList<String> contact = new ArrayList<>();
    public boolean addContact(String tele);
    public String setNextIdValue(int number);
    public String getEmpId() throws SQLException;
}
