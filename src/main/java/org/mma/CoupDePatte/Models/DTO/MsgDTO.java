package org.mma.CoupDePatte.Models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record MsgDTO(
        String content,
        String email,
        String phone,
        Date date,
        Boolean isTakeIn
){}
