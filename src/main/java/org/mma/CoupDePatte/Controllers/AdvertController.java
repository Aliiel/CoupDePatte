package org.mma.CoupDePatte.Controllers;

import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Services.AdvertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/adverts")
public class AdvertController {
    AdvertService advServ;

    public AdvertController(AdvertService advServices) {
        this.advServ = advServices;
    }


    @GetMapping("/{id}")
    public ResponseEntity<AdvertResponseDTO> getAdvertbyId(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(advServ.getById(id));

    }

    @PostMapping("/new")
    public ResponseEntity<String> newAdvert(@RequestBody AdvertDTO advertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(advServ.createAdvert(advertDTO));

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<AdvertResponseDTO> updAdvert(@PathVariable long id, @RequestBody AdvertDTO advertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(advServ.updateAdvert(id, advertDTO));

    }

}
