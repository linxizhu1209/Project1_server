package com.github.backend_1st_project.service.mapper;

import com.github.backend_1st_project.web.dto.users.LoginDTO;
import com.github.backend_1st_project.web.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface UserMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //메소드 (DTO, Entity)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    LoginDTO entityToDTO(UserEntity userEntity);

    @Named("convert")
    static String localDateTimeToString(LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }
}
