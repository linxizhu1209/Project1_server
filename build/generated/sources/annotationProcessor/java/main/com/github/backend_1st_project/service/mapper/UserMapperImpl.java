package com.github.backend_1st_project.service.mapper;

import com.github.backend_1st_project.web.dto.users.LoginDTO;
import com.github.backend_1st_project.web.entity.UserEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-08T19:02:43+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public LoginDTO entityToDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail( userEntity.getEmail() );
        loginDTO.setPassword( userEntity.getPassword() );

        return loginDTO;
    }
}
