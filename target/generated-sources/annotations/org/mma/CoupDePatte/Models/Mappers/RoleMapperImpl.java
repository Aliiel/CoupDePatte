package org.mma.CoupDePatte.Models.Mappers;

import javax.annotation.processing.Generated;
import org.mma.CoupDePatte.Models.DTO.RoleDTO;
import org.mma.CoupDePatte.Models.Entities.Role;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-17T18:49:45+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDTO toRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setName( role.getName() );

        return roleDTO;
    }

    @Override
    public Role toRole(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setName( roleDTO.getName() );

        return role;
    }

    @Override
    public Role partialUpdate(RoleDTO roleDTO, Role role) {
        if ( roleDTO == null ) {
            return role;
        }

        if ( roleDTO.getName() != null ) {
            role.setName( roleDTO.getName() );
        }

        return role;
    }
}
