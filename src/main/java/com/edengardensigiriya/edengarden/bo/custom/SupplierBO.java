package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.ItemDTO;
import com.edengardensigiriya.edengarden.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    List<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;
    List<SupplierDTO> searchSuppliers(String suppId) throws SQLException, ClassNotFoundException;
    boolean saveSuppliers(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
    boolean updateSuppliers(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
    boolean removeSuppliers(String suppId) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;

    boolean addContact(String tele);

    String getSuppId() throws SQLException;
}
