package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.ForgotPasswordBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.UserDAO;
import com.edengardensigiriya.edengarden.dto.UserDTO;
import com.edengardensigiriya.edengarden.entity.User;

public class ForgotPasswordBOImpl implements ForgotPasswordBO {
    UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean changePwd(UserDTO userDTO) {
        return userDAO.changePwd(new User(userDTO.getOldUserName(), userDTO.getOldPwd(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getConfirmation(), userDTO.getJobRoll()));
    }
}
