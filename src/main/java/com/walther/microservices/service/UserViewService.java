package com.walther.microservices.service;

import com.walther.microservices.model.UserSummaryDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserViewService {
    Page<UserSummaryDTO> getAllUsers(String name, String lastname, Integer edad, Pageable pageable);
    Optional<UserSummaryDTO> getUserById(Integer id);
}
