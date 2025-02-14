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
    private final UserService userService;


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

        User user = userService.getUserById(id);

        List<Answer> answers = new ArrayList<>(user.getAnswers());
        List<Answer> activeAnswers = new ArrayList<>();

        for (Answer userAnswer : answers) {

            if (Boolean.TRUE.equals(userAnswer.getAdvert().getIsActive())) {
                activeAnswers.add(userAnswer);
            }
        }

        if (answers.isEmpty() || activeAnswers.isEmpty()) {

            throw new NoAnswersFoundException(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return answerMapper.toDTOList(activeAnswers);
    }
}
