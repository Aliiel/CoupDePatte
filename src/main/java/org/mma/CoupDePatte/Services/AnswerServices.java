package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Entities.Answer;
import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Models.Repositories.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class AnswerServices {
    AnswerRepository answerRep;

    public AnswerServices(AnswerRepository answerRepository){
        this.answerRep=answerRepository;

    }

    public ArrayList<Answer> getByAdvert(Advert advert){
        answerRep.findByAnswerOrderByDateDesc(advert);
    }

    public void createAnswer(MsgDTO msgDTO, Advert advert, User user,String msgDefault) {
        Answer answer = new Answer();

        if (msgDTO.content() !=null) {
            answer.setContent(msgDTO.content());
        }else{
            answer.setContent(msgDefault);
        }
        answer.setIsTakeIn(msgDTO.isTakeIn());
        answer.setDate(msgDTO.date());
        answer.setAdvert(advert);
        answer.setUser(user);
        answerRep.save(answer);

    }


    public String updateAnswer(long id,MsgDTO msgDTO) {
        Answer answer = answerRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réponse avec ID " + id + " non trouvée"));

        if (msgDTO.content() !=null) {
            answer.setContent(msgDTO.content());
        }
        if (msgDTO.isTakeIn() !=null) {
            answer.setIsTakeIn(msgDTO.isTakeIn());
        }
        if (msgDTO.date() !=null) {
            answer.setDate(msgDTO.date());
        }
        answerRep.save(answer);

        return "Votre message a été modifié et renvoyé";
    }
}
