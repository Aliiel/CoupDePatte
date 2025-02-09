package org.mma.CoupDePatte.Controllers;

import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Services.AdvertServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/adverts")
public class AdvertController {
    AdvertServices advServ;

    public AdvertController(AdvertServices advService){
        this.advServ = advService;
    }


    @GetMapping("/")
    public ResponseEntity<List<AdvertResponseDTO>> getAdvertByFilter(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.status(HttpStatus.FOUND).body(advServ.getByFilter(filterDTO));

    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertResponseDTO> getAdvertbyId(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(advServ.getDTOById(id));

    }

    @PostMapping("/new")
    public ResponseEntity<String> newAdvert(@RequestBody AdvertDTO advertDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(advServ.createAdvert(advertDTO));

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<AdvertResponseDTO> updAdvert(@PathVariable long id,@RequestBody AdvertDTO advertDTO){
        return ResponseEntity.status(HttpStatus.FOUND).body(advServ.updateAdvert(id,advertDTO));

    }


    @PostMapping("/message/{id}")
    public ResponseEntity<String> addMsg(@PathVariable long id, @RequestBody MsgDTO msgDTO){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.createMsg(id,msgDTO));

    }

    @PostMapping("/answer/new/{id}/{userId}")
    public ResponseEntity<String>  addAnswer(@PathVariable long id,long userId, @RequestBody MsgDTO msgDTO){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.createAnswer(id,userId,msgDTO));

    }

    @GetMapping("/answer/consult/{id}")
    public ResponseEntity<ArrayList<MsgDTO>> displayAnswer(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.searchAnswer(id));

    }

    @PatchMapping("/update/admin/{id}")
    public void updAdvActivation(@PathVariable long id){
        advServ.updAdvActive(id);

    }

    @PatchMapping("/update/{id}")
    public void updAdvUnActivation(@PathVariable long id){
        advServ.updAdvUnActive(id);

    }

}
