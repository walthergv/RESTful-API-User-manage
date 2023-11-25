package com.walther.microservices.service;

import com.walther.microservices.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/*
Implementacion de la interfaz UserDeleteService para la edicion Profesional
*/
@Lazy // Para que no se cargue en el contexto de Spring, hasta que se necesite
@Qualifier("cloud") // Para que Spring sepa que implementacion de UserDeleteService usar
@ConditionalOnProperty(prefix = "app", name = "edition", havingValue = "Profesional")
@Service
public class UserDeleteServiceCloudImp implements UserDeleteService {
    @Autowired
    private UserClient userClient;
    @Override
    public void deleteUserById(Integer id) {
        userClient.deleteUserById(id);
    }

    @Override
    public void deleteAllUsers() {

    }
}
