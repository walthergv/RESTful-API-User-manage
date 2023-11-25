package com.walther.microservices.mapper;

import com.walther.microservices.dao.entity.UserEntity;
import com.walther.microservices.model.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserSaveMapper {
    UserEntity getUserEntityFromUserDTO(UserDTO userDTO);
    List<UserEntity> getUserEntityListFromUserDTOList(List<UserDTO> userDTOList);

}
