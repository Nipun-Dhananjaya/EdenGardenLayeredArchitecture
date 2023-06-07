package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.User;

public interface UserDAO extends CrudDAO<User,String> {
    boolean changePwd(User entity);
    boolean changeUser(User entity);
    boolean IsCorrect(User entity);
}
