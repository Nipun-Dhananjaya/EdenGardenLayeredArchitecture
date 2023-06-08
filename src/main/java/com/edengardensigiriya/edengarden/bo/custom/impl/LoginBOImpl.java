package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.LoginBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.UserDAO;
import com.edengardensigiriya.edengarden.dto.UserDTO;
import com.edengardensigiriya.edengarden.entity.User;

public class LoginBOImpl implements LoginBO {
    UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean isCorrect(UserDTO userDTO) {
        return userDAO.IsCorrect(new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getJobRoll()));
    }
}
