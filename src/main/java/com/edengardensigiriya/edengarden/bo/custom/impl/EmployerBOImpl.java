package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.EmployerBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.EmployerDAO;
import com.edengardensigiriya.edengarden.dto.EmployerDTO;
import com.edengardensigiriya.edengarden.entity.Employer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployerBOImpl implements EmployerBO {
    EmployerDAO employerDAO= (EmployerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYER);
    @Override
    public List<EmployerDTO> getAllEmployers() throws SQLException, ClassNotFoundException {
        List<EmployerDTO> empList = new ArrayList<>();

        for (Employer employer : employerDAO.getAll()) {
            empList.add(new EmployerDTO(
                    employer.getEmpId(),
                    employer.getEmpName(),
                    employer.getNic(),
                    employer.getAddress(),
                    employer.getEmail(),
                    employer.getContact(),
                    employer.getDob(),
                    employer.getGender(),
                    employer.getJobRole(),
                    employer.getMonthlySalary(),
                    employer.getEnteredDate(),
                    employer.getResignedDate()
            ));
        }
        return empList;
    }

    @Override
    public List<EmployerDTO> searchEmployers(String empId) throws SQLException, ClassNotFoundException {
        List<EmployerDTO> empList = new ArrayList<>();
        for (Employer employer : employerDAO.search(empId)) {
            empList.add(new EmployerDTO(
                    employer.getEmpId(),
                    employer.getEmpName(),
                    employer.getNic(),
                    employer.getAddress(),
                    employer.getEmail(),
                    employer.getContact(),
                    employer.getDob(),
                    employer.getGender(),
                    employer.getJobRole(),
                    employer.getMonthlySalary(),
                    employer.getEnteredDate(),
                    employer.getResignedDate()
            ));
        }
        return empList;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return employerDAO.newIdGenerate();
    }

    @Override
    public boolean saveEmployer(EmployerDTO employerDTO) throws SQLException, ClassNotFoundException {
        return employerDAO.save(new Employer(employerDTO.getEmpId(), employerDTO.getEmpName(), employerDTO.getNic(),
                employerDTO.getAddress(), employerDTO.getEmail(), employerDTO.getContact(), employerDTO.getDob(),
                employerDTO.getGender(), employerDTO.getJobRole(), employerDTO.getMonthlySalary(), employerDTO.getEnteredDate(), employerDTO.getResignedDate()));
    }

    @Override
    public boolean updateEmployer(EmployerDTO employerDTO) throws SQLException, ClassNotFoundException {
        return employerDAO.update(new Employer(employerDTO.getEmpId(), employerDTO.getEmpName(), employerDTO.getNic(),
                employerDTO.getAddress(), employerDTO.getEmail(), employerDTO.getContact(), employerDTO.getDob(),
                employerDTO.getGender(), employerDTO.getJobRole(), employerDTO.getMonthlySalary(), employerDTO.getEnteredDate(), employerDTO.getResignedDate()));
    }

    @Override
    public boolean addContact(String tele) {
        return employerDAO.addContact(tele);
    }

    @Override
    public String getEmpId() throws SQLException {
        return employerDAO.getEmpId();
    }
}
