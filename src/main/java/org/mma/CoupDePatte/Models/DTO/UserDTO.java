package org.mma.CoupDePatte.Models.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDTO {

    private String lastName;
    private String firstName;
    private String address;
    private String phone;
    private CityDTO city;
}
