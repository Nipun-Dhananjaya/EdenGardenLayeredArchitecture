package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.UserDTO;

public interface ForgotPasswordBO extends SuperBO {
    boolean changePwd(UserDTO userDTO);
}
