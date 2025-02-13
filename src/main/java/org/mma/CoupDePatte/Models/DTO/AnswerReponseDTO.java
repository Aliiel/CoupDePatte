package org.mma.CoupDePatte.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerReponseDTO {

    private String content;
    private Date date;
    private Boolean isTakeIn;
    private AdvertResponseDTO advert;
}
