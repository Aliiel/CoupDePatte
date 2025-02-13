package org.mma.CoupDePatte.Controllers;

import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Services.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/adverts")
public class AdvertController {

    AdvertService advServ;

    @Autowired
    public AdvertController(AdvertService advServices) {
        this.advServ = advServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertResponseDTO> getAdvertbyId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.getDTOById(id));

    }

    @PostMapping("/new")
    public ResponseEntity<String> newAdvert(@RequestBody AdvertDTO advertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(advServ.createAdvert(advertDTO));

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<AdvertResponseDTO> updAdvert(@PathVariable long id,@RequestBody AdvertDTO advertDTO){
        return ResponseEntity.status(HttpStatus.FOUND).body(advServ.updateAdvert(id,advertDTO));

    }

    @PatchMapping("/update/admin/active/{id}")
    public ResponseEntity<Void> updAdvActivation(@PathVariable long id){
        advServ.updAdvActive(id);

        return null;
    }

    @PatchMapping("/update/active/{id}")
    public ResponseEntity<Void> updAdvUnActivation(@PathVariable long id){
        advServ.updAdvUnActive(id);

        return null;
    }

    @PostMapping("/message/{id}")
    public ResponseEntity<String> addMsg(@PathVariable long id, @RequestBody MsgDTO msgDTO){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.createMsg(id,msgDTO));

    }

    @PostMapping("/answer/new/{id}/{email}")
    public ResponseEntity<String>  addAnswer(@PathVariable long id,@PathVariable String email, @RequestBody MsgDTO msgDTO){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.createAnswer(id,email,msgDTO));

    }

    @PatchMapping("/answer/update/{id}")
    public ResponseEntity<String>  updateAnswer(@PathVariable long id, @RequestBody MsgDTO msgDTO){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.updAnswer(id,msgDTO));

    }

    @GetMapping("/answer/consult/{id}")
    public ResponseEntity<ArrayList<MsgDTO>> displayAnswer(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(advServ.searchAnswer(id));

    }


}
