package org.mma.CoupDePatte.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private String content;
    private Date date;
    private Boolean isTakeIn;
    private AdvertDTO advert;
}
