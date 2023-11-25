package com.walther.microservices.client;

import com.walther.microservices.model.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserClientImp implements UserClient{
    @Override
    public Optional<UserDTO> getUserById(Integer id) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        UserDTO userDTO = restTemplate.getForObject(url, UserDTO.class);
        return Optional.ofNullable(userDTO); // Si es null, devuelve un Optional vacio
    }

    @Override
    public void deleteUserById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        restTemplate.delete(url);
    }
}
