package com.walther.microservices.service;

public interface UserDeleteService {
    void deleteUserById(Integer id);

    // Elimina todos los usuarios
    void deleteAllUsers();
}
