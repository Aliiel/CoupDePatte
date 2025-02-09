package org.mma.CoupDePatte.Models.Mappers;

import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.Entities.Answer;
import org.mma.CoupDePatte.Models.Entities.Message;
import org.mma.CoupDePatte.Models.Entities.Pet;

public class MsgMapper {
    public MsgDTO msgToDTO (Message message){
        return new MsgDTO(
                message.getContent(),
                message.getEmail(),
                message.getPhone(),
                message.getDate(),
                message.getIsTakeIn()
        );
    }

    public MsgDTO answerToDTO (Answer answer){
        return new MsgDTO(
                answer.getContent(),
                answer.getUser().getEmail(),
                answer.getUser().getPhone(),
                answer.getDate(),
                answer.getIsTakeIn()
        );

    }

}
