package com.walther.microservices.mapper;

import com.walther.microservices.dao.entity.UserEntity;
import com.walther.microservices.model.UserSummaryDTO;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserSummaryMapper {
    UserSummaryDTO getUserSummaryDTOFromUserEntity(UserEntity userEntity);
    List<UserSummaryDTO> getUserSummaryDTOListFromUserEntityList(List<UserEntity> userEntityList);
}
