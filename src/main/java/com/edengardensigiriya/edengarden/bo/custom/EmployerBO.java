package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.EmployerDTO;
import com.edengardensigiriya.edengarden.entity.Employer;

import java.sql.SQLException;
import java.util.List;

public interface EmployerBO extends SuperBO {
    List<EmployerDTO> getAllEmployers() throws SQLException, ClassNotFoundException;
    List<EmployerDTO> searchEmployers(String empId) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;

    boolean saveEmployer(EmployerDTO employerDTO) throws SQLException, ClassNotFoundException;

    boolean updateEmployer(EmployerDTO employerDTO) throws SQLException, ClassNotFoundException;

    boolean addContact(String tele);

    String getEmpId() throws SQLException;
}
