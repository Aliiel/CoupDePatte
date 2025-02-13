package org.mma.CoupDePatte.Services;

import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Exceptions.NoAnswersFoundException;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.AnswerReponseDTO;
import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Entities.Answer;
import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Models.Mappers.AnswerMapper;
import org.mma.CoupDePatte.Models.Repositories.AnswerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRep;
    private final AnswerMapper answerMapper;


    public ArrayList<Answer> getByAdvert(Advert advert) {
        return answerRep.findByAdvertOrderByDateDesc(advert);
    }


    public void createAnswer(MsgDTO msgDTO, Advert advert, User user, String msgDefault) {
        Answer answer = new Answer();

        if (msgDTO.content() != null) {
            answer.setContent(msgDTO.content());
        } else {
            answer.setContent(msgDefault);
        }
        answer.setIsTakeIn(msgDTO.isTakeIn());
        answer.setDate(msgDTO.date());
        answer.setAdvert(advert);
        answer.setUser(user);
        answerRep.save(answer);
    }


    public String updateAnswer(long id, MsgDTO msgDTO) {
        Answer answer = answerRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réponse avec ID " + id + " non trouvée"));

        if (msgDTO.content() != null) {
            answer.setContent(msgDTO.content());
        }

        answer.setIsTakeIn(msgDTO.isTakeIn());

        if (msgDTO.date() != null) {
            answer.setDate(msgDTO.date());
        }
        answerRep.save(answer);

        return "Votre message a été modifié et renvoyé";
    }


    public List<AnswerReponseDTO> getAnswersByUserId(Long id) {

        List<AnswerReponseDTO> userAnswers = answerMapper.toDTOList(answerRep.findByUser_Id(id));

        List<AnswerReponseDTO> activeAnswers = new ArrayList<>();

        for (AnswerReponseDTO userAnswer : userAnswers) {

            if (Boolean.TRUE.equals(userAnswer.getAdvert().isActive())) {
                activeAnswers.add(userAnswer);
            }
        }

        if (userAnswers.isEmpty() || activeAnswers.isEmpty()) {

            throw new NoAnswersFoundException(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return activeAnswers;
    }
}
