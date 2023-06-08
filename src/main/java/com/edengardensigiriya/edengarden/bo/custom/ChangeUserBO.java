package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.UserDTO;
import com.edengardensigiriya.edengarden.entity.User;

public interface ChangeUserBO extends SuperBO {
    boolean changeUser(UserDTO userDTO);
}
