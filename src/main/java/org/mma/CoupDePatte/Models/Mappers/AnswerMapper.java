package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mma.CoupDePatte.Models.DTO.AnswerReponseDTO;
import org.mma.CoupDePatte.Models.Entities.Answer;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AdvertMapStructMapper.class, UserMapper.class})
public interface AnswerMapper {

    AnswerReponseDTO toAnswerResponseDTO(Answer answer);

    Answer toAnswer(AnswerReponseDTO answerReponseDTO);

    List<AnswerReponseDTO> toDTOList(List<Answer> answers);
}
