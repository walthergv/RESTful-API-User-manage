package com.walther.microservices.client;

import com.walther.microservices.model.UserDTO;

import java.util.Optional;

public interface UserClient {
    Optional<UserDTO> getUserById(Integer id);
    void deleteUserById(Integer id);
}
