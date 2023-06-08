package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    List<CustomerDTO> searchCustomers(String custId) throws SQLException, ClassNotFoundException;

    boolean addContact(String contactNo);
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    String getCustId();
}
