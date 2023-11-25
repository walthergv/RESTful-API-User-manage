package com.walther.microservices.service;

import com.walther.microservices.dao.entity.UserEntity;
import com.walther.microservices.dao.repositories.UserRepository;
import com.walther.microservices.errorHandling.ValidationException;
import com.walther.microservices.mapper.UserSaveMapper;
import com.walther.microservices.mapper.UserSummaryMapper;
import com.walther.microservices.model.UserDTO;
import com.walther.microservices.model.UserSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
@Qualifier("BD")
@ConditionalOnProperty(prefix = "app", name = "edition", havingValue = "Community")
@Service
public class UserSaveServiceImp implements UserSaveService{
    @Autowired
    private UserSaveMapper userSaveMapper;
    @Autowired
    private UserSummaryMapper userSummaryMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Validator validator;

    private void validateUserDTO(UserDTO userDTO, List<String> validationErrors) {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(userDTO, "userDTO");
        validator.validate(userDTO, result);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String errorMessage = fieldError.getField() + ": " + fieldError.getDefaultMessage();
                validationErrors.add(errorMessage);
            });
        }
    }
    @Override
    public UserSummaryDTO saveUser(UserDTO userDTO) {

        List<String> validationErrors = new ArrayList<>();
        validateUserDTO(userDTO, validationErrors);

        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }

        UserEntity userEntityFromUserDTO = userSaveMapper.getUserEntityFromUserDTO(userDTO);

        UserEntity savedUserEntity = userRepository.save(userEntityFromUserDTO);
        return userSummaryMapper.getUserSummaryDTOFromUserEntity(savedUserEntity);
    }

    @Override
    public List<UserSummaryDTO> saveAllUsers(List<UserDTO> userDTOList) {
        if (userDTOList.isEmpty()) {
            throw new ValidationException(List.of("error: userDTOList is empty"));
        }

        List<UserEntity> validUserEntities = new ArrayList<>();

        for(UserDTO userDTO : userDTOList){
            List<String> validationErrors = new ArrayList<>();
            validateUserDTO(userDTO, validationErrors);

            if(validationErrors.isEmpty()){
                UserEntity userEntityFromUserDTO = userSaveMapper.getUserEntityFromUserDTO(userDTO);
                validUserEntities.add(userEntityFromUserDTO);
            }
        }

        List<UserEntity> savedUsersEntities = userRepository.saveAll(validUserEntities);
        return userSummaryMapper.getUserSummaryDTOListFromUserEntityList(savedUsersEntities);
    }
}