package com.walther.microservices.service;

import com.walther.microservices.dao.entity.UserEntity;
import com.walther.microservices.dao.repositories.UserRepository;
import com.walther.microservices.mapper.UserSummaryMapper;
import com.walther.microservices.model.UserSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Qualifier("BD")
@ConditionalOnProperty(prefix = "app", name = "edition", havingValue = "Community")
@Service
public class UserViewServiceImp implements UserViewService{
    @Autowired
    private UserSummaryMapper userSummaryMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserSummaryDTO> getAllUsers(String name, String lastname, Integer edad, Pageable pageable) {

        List<UserEntity> filteredUsers = userRepository.findAll().stream()
                .filter(u -> name == null || u.getName().toLowerCase().startsWith(name.toLowerCase()))
                .filter(u -> lastname == null || u.getLastName().toLowerCase().startsWith(lastname.toLowerCase()))
                .filter(u -> edad == null || u.getEdad().equals(edad))
                .toList();

         int start = (int) pageable.getOffset();
         if (start > filteredUsers.size()) {
             return Page.empty();
         }

         int end = Math.min((start + pageable.getPageSize()), filteredUsers.size());

         List<UserEntity> pageUserEntities1 = filteredUsers.subList(start, end);

        return new PageImpl<>(
                userSummaryMapper.getUserSummaryDTOListFromUserEntityList(pageUserEntities1),
                pageable, filteredUsers.size());
    }

    @Override
    public Optional<UserSummaryDTO> getUserById(Integer id) {
        if (id == null) {
            throw new NullPointerException("Id can not be null");
        }
        Optional<UserEntity> optUserEntity = userRepository.findById(id);
        UserSummaryDTO userSummaryDTO = userSummaryMapper
                .getUserSummaryDTOFromUserEntity(optUserEntity.orElseThrow(() ->
                        new NullPointerException("User not found with id: " + id + " to get")
                ));
        return Optional.of(userSummaryDTO);
    }
}
