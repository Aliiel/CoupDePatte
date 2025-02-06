package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.*;
import org.mma.CoupDePatte.Models.DTO.UserDTO;
import org.mma.CoupDePatte.Models.Entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class, CityMapper.class})
public interface UserMapper {

    UserDTO toUserDTO(User user);

    User toEntity(UserDTO userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDTO userDTO, @MappingTarget User user);
}
