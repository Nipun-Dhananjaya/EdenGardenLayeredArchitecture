package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.UserDTO;

public interface LoginBO extends SuperBO {
    boolean isCorrect(UserDTO userDTO);
}
