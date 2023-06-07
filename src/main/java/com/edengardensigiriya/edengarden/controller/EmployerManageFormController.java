package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.EmployerDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.*;
import com.edengardensigiriya.edengarden.dto.tm.EmployerTM;
import com.edengardensigiriya.edengarden.entity.Employer;
import com.edengardensigiriya.edengarden.util.RegExPatterns;
import com.edengardensigiriya.edengarden.util.SendEmail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployerManageFormController {
    public TextField idTxt;
    public TextField nameTxt;
    public TextField nicTxt;
    public TextField emailTxt;
    public TextField addressTxt;
    public TextField contactTxt;
    public RadioButton maleRdBtn;
    public ToggleGroup gender;
    public RadioButton femaleRdBtn;
    public TableView empTbl;
    public Button addBtn;
    public TextField jobRolTxt;
    public DatePicker strtDtDtPck;
    public DatePicker endDtPkr;
    public DatePicker dobDtPck;
    public Button updateBtn;
    public AnchorPane employerRoot;
    public TextField empSalary;
    public TableColumn columnId;
    public TableColumn columnName;
    public TableColumn columnNic;
    public TableColumn columnAddress;
    public TableColumn columnEmail;
    public TableColumn columnContact;
    public TableColumn columnDob;
    public TableColumn columnGender;
    public TableColumn columnJobrole;
    public TableColumn columnSalary;
    public TableColumn columnStartDate;
    public TableColumn columnResignedDate;
    public TextField contactTxt1;
    public Button addContactBtn1;
    public ComboBox contactCmbBx;
    static String con;
    EmployerDAO employerDAO= (EmployerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        getAllEmployers();
    }

    private void getAllEmployers() throws SQLException, ClassNotFoundException {
        ObservableList<EmployerTM> obList = FXCollections.observableArrayList();
        List<EmployerDTO> empList = new ArrayList<>();//employerDAO.getAll();

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
        for (EmployerDTO employer : empList) {
            obList.add(new EmployerTM(
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
        empTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        columnNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        columnDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnJobrole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("monthlySalary"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("enteredDate"));
        columnResignedDate.setCellValueFactory(new PropertyValueFactory<>("resignedDate"));
    }
    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            employerDAO.contact.clear();
            List<EmployerDTO> empList = new ArrayList<>();
            for (Employer employer : employerDAO.search(idTxt.getText())) {
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
            if (!empList.isEmpty()){
                for (EmployerDTO employer : empList) {
                    nameTxt.setText(employer.getEmpName());
                    nicTxt.setText(employer.getNic());
                    emailTxt.setText(employer.getEmail());
                    addressTxt.setText(employer.getAddress());
                    contactCmbBx.setItems(getContactObList(employer.getContact()));
                    jobRolTxt.setText(employer.getJobRole());
                    dobDtPck.setValue(LocalDate.parse(employer.getDob()));
                    strtDtDtPck.setValue(LocalDate.parse(employer.getEnteredDate()));
                    System.out.println(employer.getResignedDate());
                    if (employer.getResignedDate()!=null) {
                        endDtPkr.setValue(LocalDate.parse(employer.getResignedDate()));
                    }
                    empSalary.setText(employer.getMonthlySalary());
                    if (employer.getGender().equals("MALE")){
                        maleRdBtn.setSelected(true);
                    }else{
                        femaleRdBtn.setSelected(true);
                    }
                }
                idTxt.setDisable(true);
            }else{
                new Alert(Alert.AlertType.WARNING, "Employer ID Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addEmployerOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= employerDAO.save(new Employer(employerDAO.newIdGenerate(), nameTxt.getText(), nicTxt.getText(),
                        addressTxt.getText(), emailTxt.getText(), String.join(" , ", employerDAO.contact),String.valueOf(dobDtPck.getValue()),
                        maleRdBtn.isSelected() ? "MALE" : "FEMALE", jobRolTxt.getText(),empSalary.getText(),String.valueOf(strtDtDtPck.getValue()),String.valueOf(endDtPkr.getValue())));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Employer Added!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                sendMail("Add");
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= employerDAO.update(new Employer(idTxt.getText(), nameTxt.getText(), nicTxt.getText(),
                        addressTxt.getText(), emailTxt.getText(), String.join(" , ", employerDAO.contact),String.valueOf(dobDtPck.getValue()),
                        maleRdBtn.isSelected() ? "MALE" : "FEMALE", jobRolTxt.getText(),empSalary.getText(),String.valueOf(strtDtDtPck.getValue()),String.valueOf(endDtPkr.getValue())));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Employer Updated!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                sendMail("Update");
                idTxt.setDisable(false);
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
    public void resetPage() throws SQLException, ClassNotFoundException {
        idTxt.setText("");
        nameTxt.setText("");
        nicTxt.setText("");
        emailTxt.setText("");
        addressTxt.setText("");
        contactTxt.setText("");
        emailTxt.setText("");
        jobRolTxt.setText("");
        empSalary.setText("");
        employerDAO.contact.clear();
        setCellValueFactory();
        getAllEmployers();
    }

    private ObservableList getContactObList(String custContact) {
        String[] contacts=custContact.split(" , ");
        List<String> conList = Arrays.asList(contacts);
        ObservableList<String> cont = FXCollections.observableList(conList);
        int length=contacts.length;
        while (length>0){
            employerDAO.contact.add(contacts[length-1]);
            length-=1;
        }
        return cont;
    }

    public void addContactOnAction(ActionEvent actionEvent) {
        System.out.println(con);
        if (contactCmbBx.getItems().contains(con)) {
            employerDAO.contact.remove(con);
        }
        //System.out.println("con:   "/*+contactTxt.getText()*/);
        if (RegExPatterns.getMobilePattern().matcher(contactTxt.getText()).matches()){
            boolean isAlreadyHas = employerDAO.addContact(contactTxt.getText());
            if (!isAlreadyHas) {
                new Alert(Alert.AlertType.WARNING, "Contact Already Added!").showAndWait();
            } else {
                ObservableList<String> cont = FXCollections.observableList(employerDAO.contact);
                contactCmbBx.setItems(cont);
                contactCmbBx.setValue("Contact List");
                contactTxt.setText("");
            }
        }
        else{
            new Alert(Alert.AlertType.WARNING, "Invalid Contact Number!").showAndWait();
        }
    }
    public void setTxtBxValueOnAction(ActionEvent actionEvent) {
        contactTxt.setText(String.valueOf(contactCmbBx.getSelectionModel().getSelectedItem()));
        con = contactTxt.getText();
    }
    private boolean isCorrectPattern(){
        if (RegExPatterns.getNamePattern().matcher(nameTxt.getText()).matches()  && (RegExPatterns.getIdPattern().matcher(nicTxt.getText()).matches()
                ||RegExPatterns.getOldIDPattern().matcher(nicTxt.getText()).matches() ) && RegExPatterns.getAddressPattern().matcher(addressTxt.getText()).matches()
                && RegExPatterns.getDoublePattern().matcher(empSalary.getText()).matches() && RegExPatterns.getEmailPattern().matcher(emailTxt.getText()).matches()){
            return true;
        }
        return false;
    }
    public void sendMail(String status) throws MessagingException, GeneralSecurityException, IOException, SQLException {
        SendEmail.sendMail(emailTxt.getText(),
                (status.equals("Add")?"Register As Employer!":"Update Employer Details!"),
                "Your Employer ID:"+(status.equals("Add")? employerDAO.getEmpId():idTxt.getText())+"\nName:"+nameTxt.getText()+
                        "\nNIC: "+ nicTxt.getText()+"\nJob Role: "+ jobRolTxt.getText()+"\nService Start From:"+strtDtDtPck.getValue()+"\nService End:"+endDtPkr.getValue()+
                        "\n"+(status.equals("Add")?"Registered As Supplier Successfully!":"Employer Details Update Successfully"+"\n\nThank you!\n\nHotel Eden Garden,\nInamaluwa,\nSeegiriya"));
    }
}
