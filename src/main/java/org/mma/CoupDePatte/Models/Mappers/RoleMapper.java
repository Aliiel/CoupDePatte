package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.*;
import org.mma.CoupDePatte.Models.DTO.RoleDTO;
import org.mma.CoupDePatte.Models.Entities.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    RoleDTO toRoleDTO(Role role);

    Role toRole(RoleDTO roleDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role partialUpdate(RoleDTO roleDTO, @MappingTarget Role role);
}
