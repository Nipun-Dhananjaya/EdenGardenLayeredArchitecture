package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.SupplierBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.SupplierDAO;
import com.edengardensigiriya.edengarden.dto.SupplierDTO;
import com.edengardensigiriya.edengarden.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO= (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public List<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> supplierList = new ArrayList<>();
        for (Supplier supplier : supplierDAO.getAll()) {
            supplierList.add(new SupplierDTO(
                    supplier.getSuppId(),
                    supplier.getSuppName(),
                    supplier.getSuppAddress(),
                    supplier.getSuppEmail(),
                    supplier.getSuppContact(),
                    supplier.getItemType(),
                    supplier.getContactStartDate(),
                    supplier.getContactEndDate()
            ));
        }
        return supplierList;
    }

    @Override
    public List<SupplierDTO> searchSuppliers(String suppId) throws SQLException, ClassNotFoundException {
        List<SupplierDTO> suppList = new ArrayList<>();
        for (Supplier supplier : supplierDAO.search(suppId)) {
            suppList.add(new SupplierDTO(
                    supplier.getSuppId(),
                    supplier.getSuppName(),
                    supplier.getSuppAddress(),
                    supplier.getSuppEmail(),
                    supplier.getSuppContact(),
                    supplier.getItemType(),
                    supplier.getContactStartDate(),
                    supplier.getContactEndDate()
            ));
        }
        return suppList;
    }

    @Override
    public boolean saveSuppliers(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplierDTO.getSuppId(),
                supplierDTO.getSuppName(), supplierDTO.getSuppAddress(), supplierDTO.getSuppEmail(), supplierDTO.getSuppContact(),
                supplierDTO.getItemType(), supplierDTO.getContactStartDate(), supplierDTO.getContactEndDate()));
    }

    @Override
    public boolean updateSuppliers(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplierDTO.getSuppId(),
                supplierDTO.getSuppName(), supplierDTO.getSuppAddress(), supplierDTO.getSuppEmail(), supplierDTO.getSuppContact(),
                supplierDTO.getItemType(), supplierDTO.getContactStartDate(), supplierDTO.getContactEndDate()));
    }

    @Override
    public boolean removeSuppliers(String suppId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(suppId);
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return supplierDAO.newIdGenerate();
    }

    @Override
    public boolean addContact(String tele) {
        return supplierDAO.addContact(tele);
    }

    @Override
    public String getSuppId() throws SQLException {
        return supplierDAO.getSuppId();
    }
}
