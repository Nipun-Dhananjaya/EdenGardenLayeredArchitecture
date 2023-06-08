package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.ChangeUserBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.UserDAO;
import com.edengardensigiriya.edengarden.dto.UserDTO;
import com.edengardensigiriya.edengarden.entity.User;

public class ChangeUserBOImpl implements ChangeUserBO {
    UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean changeUser(UserDTO userDTO) {
        return userDAO.changeUser(new User(userDTO.getOldUserName(), userDTO.getOldPwd(), userDTO.getEmpId(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getJobRoll(),0));
    }
}
