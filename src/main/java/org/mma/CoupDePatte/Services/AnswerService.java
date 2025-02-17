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


@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final UserService userService;

    public AnswerService(AnswerRepository answerRepository,AnswerMapper answerMapper, UserService userService){
        this.answerRepository=answerRepository;
        this.answerMapper=answerMapper;
        this.userService=userService;
    }

    public ArrayList<Answer> getByAdvert(Advert advert) {
        return answerRepository.findByAdvertOrderByDateDesc(advert);
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
        answerRepository.save(answer);
    }


    public void updateAnswer(long id, MsgDTO msgDTO) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réponse avec ID " + id + " non trouvée"));

        if (msgDTO.content() != null) {
            answer.setContent(msgDTO.content());
        }

        answer.setIsTakeIn(msgDTO.isTakeIn());

        if (msgDTO.date() != null) {
            answer.setDate(msgDTO.date());
        }
        answerRepository.save(answer);

    }

    public List<AnswerReponseDTO> getAnswersByUserId(Long id) {

        List<Answer> answers = answerRep.findByUser_Id(id);
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

    public Advert getAdvert (Long id){
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réponse avec ID " + id + " non trouvée"));
        return answer.getAdvert();
    }
}
