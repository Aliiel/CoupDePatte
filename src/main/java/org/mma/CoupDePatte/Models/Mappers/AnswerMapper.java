package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mma.CoupDePatte.Models.DTO.AnswerDTO;
import org.mma.CoupDePatte.Models.Entities.Answer;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AdvertMapStructMapper.class, UserMapper.class})
public interface AnswerMapper {

    AnswerDTO toAnswerDTO(Answer answer);

    Answer toAnswer(AnswerDTO answerDTO);

    List<AnswerDTO> toDTOList(List<Answer> answers);
}
