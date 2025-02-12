package org.mma.CoupDePatte.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {

    private String lastName;
    private String firstName;
    private String address;
    private String phone;
    private CityDTO city;
    private RoleDTO role;
}
