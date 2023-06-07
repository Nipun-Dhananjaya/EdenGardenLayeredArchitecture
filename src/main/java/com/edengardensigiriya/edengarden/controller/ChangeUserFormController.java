package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.UserDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.entity.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.SQLException;

public class ChangeUserFormController{
    public TextField txtOldUserName;
    public Button changeUserBtn;
    public TextField txtNewUserEmpId;
    public PasswordField txtOldPwd;
    public PasswordField txtNewPwd;
    public ToggleGroup user;
    public TextField txtNewUserName;
    public RadioButton receptionistRdBtn;
    public RadioButton managerRdBtn;
    UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    public void escapeOldPwdOnAction(ActionEvent actionEvent) {
        txtOldPwd.requestFocus();
    }

    public void ChangeUserOnAction(ActionEvent actionEvent) throws SQLException {
        changeUser();
    }

    public void escapeNewUserNameOnAction(ActionEvent actionEvent) {
        txtNewUserName.requestFocus();
    }

    public void escapeNewUserIdOnAction(ActionEvent actionEvent) {
        txtNewUserEmpId.requestFocus();
    }

    public void changeUserOnAction(ActionEvent actionEvent) throws SQLException {
        changeUser();
    }

    private void changeUser() throws SQLException {
        try{
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            if (userDAO.changeUser(new User(txtOldUserName.getText(), txtOldPwd.getText(),txtNewUserEmpId.getText(),txtNewUserName.getText(),txtNewPwd.getText(),receptionistRdBtn.isSelected() ? "Receptionist":"Manager",0))) {
                new Alert(Alert.AlertType.INFORMATION, "User Changed Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                resetPage();
            }else{
                new Alert(Alert.AlertType.WARNING, "Submitted details are incorrect!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    private void resetPage() {
        txtOldUserName.setText("");
        txtNewUserName.setText("");
        txtNewPwd.setText("");
        txtOldPwd.setText("");
        txtNewUserEmpId.setText("");
    }

    public void escapeNewPwdOnAction(ActionEvent actionEvent) {
        txtNewPwd.requestFocus();
    }
}
