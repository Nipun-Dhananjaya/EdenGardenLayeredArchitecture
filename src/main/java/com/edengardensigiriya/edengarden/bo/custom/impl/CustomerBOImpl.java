package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.CustomerBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.CustomerDAO;
import com.edengardensigiriya.edengarden.dto.CustomerDTO;
import com.edengardensigiriya.edengarden.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customer= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> cusList = new ArrayList<>();
        for (Customer cust:customer.getAll()) {
            cusList.add(new CustomerDTO(
                    cust.getCustId(),
                    cust.getCustName(),
                    cust.getCustNic(),
                    cust.getCustEmail(),
                    cust.getCustAddress(),
                    cust.getCustContact(),
                    cust.getCustGender()
            ));
        }
        return cusList;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String custId) throws SQLException, ClassNotFoundException {
        List<CustomerDTO> cusList = new ArrayList<>();
        for (Customer cust:customer.search(custId)) {
            cusList.add(new CustomerDTO(
                    cust.getCustId(),
                    cust.getCustName(),
                    cust.getCustNic(),
                    cust.getCustEmail(),
                    cust.getCustAddress(),
                    cust.getCustContact(),
                    cust.getCustGender()
            ));
        }
        return cusList;
    }

    @Override
    public boolean addContact(String contactNo) {
        return customer.addContact(contactNo);
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customer.save(new Customer(customerDTO.getCustId(),
                customerDTO.getCustName(), customerDTO.getCustNic(), customerDTO.getCustAddress(), customerDTO.getCustEmail(), customerDTO.getCustContact(),
                customerDTO.getCustGender()));
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return customer.newIdGenerate();
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customer.update(new Customer(customerDTO.getCustId(),
                customerDTO.getCustName(), customerDTO.getCustNic(), customerDTO.getCustAddress(), customerDTO.getCustEmail(), customerDTO.getCustContact(),
                customerDTO.getCustGender()));
    }

    @Override
    public String getCustId() {
        return customer.getCustId();
    }
}
