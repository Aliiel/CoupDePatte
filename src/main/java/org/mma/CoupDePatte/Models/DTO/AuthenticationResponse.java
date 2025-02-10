package org.mma.CoupDePatte.Models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(@JsonProperty("access_token")
                                     String accessToken) {

}