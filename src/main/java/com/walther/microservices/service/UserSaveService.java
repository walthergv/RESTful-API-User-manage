package com.walther.microservices.service;

import com.walther.microservices.model.UserDTO;
import com.walther.microservices.model.UserSummaryDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface UserSaveService {
    UserSummaryDTO saveUser(UserDTO userDTO);
    List<UserSummaryDTO> saveAllUsers(List<UserDTO> userDTOList);
}
