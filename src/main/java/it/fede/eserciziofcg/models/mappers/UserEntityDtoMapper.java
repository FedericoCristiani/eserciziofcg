package it.fede.eserciziofcg.models.mappers;


import it.fede.eserciziofcg.models.dto.UserDto;
import it.fede.eserciziofcg.models.entities.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityDtoMapper {
    UserDto userEntityToDto(UserEntity source);
    UserEntity userDtoToEntity(UserDto destination);
    List<UserDto> userDtoListToEntities(List<UserEntity> users);
}