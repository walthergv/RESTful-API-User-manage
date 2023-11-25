package com.walther.microservices.service;

import com.walther.microservices.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Qualifier("BD")
@ConditionalOnProperty(prefix = "app", name = "edition", havingValue = "Community")
@Service
public class UserDeleteServiceImp implements UserDeleteService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void deleteUserById(Integer id) {
        if (!userRepository.existsById(id)){
            throw new RuntimeException("User not found with id: " + id + " to delete");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        if (userRepository.count() == 0){
            throw new RuntimeException("There are no users to delete");
        }
        userRepository.deleteAll();
    }
}
