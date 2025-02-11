package org.mma.CoupDePatte.Models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationResponse(@JsonProperty("access_token")
                                String accessToken, String username, String email, String roleName) {
}
