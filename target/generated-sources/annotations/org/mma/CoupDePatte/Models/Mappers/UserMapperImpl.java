package org.mma.CoupDePatte.Models.Mappers;

import javax.annotation.processing.Generated;
import org.mma.CoupDePatte.Models.DTO.UserDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Entities.Role;
import org.mma.CoupDePatte.Models.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-17T22:20:51+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private CityMapper cityMapper;

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setLastName( user.getLastName() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setAddress( user.getAddress() );
        userDTO.setPhone( user.getPhone() );
        userDTO.setCity( cityMapper.toCityDTO( user.getCity() ) );
        userDTO.setRole( roleMapper.toRoleDTO( user.getRole() ) );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setLastName( userDTO.getLastName() );
        user.setFirstName( userDTO.getFirstName() );
        user.setAddress( userDTO.getAddress() );
        user.setPhone( userDTO.getPhone() );
        user.setCity( cityMapper.toEntity( userDTO.getCity() ) );
        user.setRole( roleMapper.toRole( userDTO.getRole() ) );

        return user;
    }

    @Override
    public void partialUpdate(UserDTO userDTO, User user) {
        if ( userDTO == null ) {
            return;
        }

        if ( userDTO.getLastName() != null ) {
            user.setLastName( userDTO.getLastName() );
        }
        if ( userDTO.getFirstName() != null ) {
            user.setFirstName( userDTO.getFirstName() );
        }
        if ( userDTO.getAddress() != null ) {
            user.setAddress( userDTO.getAddress() );
        }
        if ( userDTO.getPhone() != null ) {
            user.setPhone( userDTO.getPhone() );
        }
        if ( userDTO.getCity() != null ) {
            if ( user.getCity() == null ) {
                user.setCity( new City() );
            }
            cityMapper.partialUpdate( userDTO.getCity(), user.getCity() );
        }
        if ( userDTO.getRole() != null ) {
            if ( user.getRole() == null ) {
                user.setRole( new Role() );
            }
            roleMapper.partialUpdate( userDTO.getRole(), user.getRole() );
        }
    }
}
