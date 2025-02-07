package org.mma.CoupDePatte.Controllers;

import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.DTO.PetResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Pet;
import org.mma.CoupDePatte.Services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {
    PetService petServ;

    public PetController(PetService petService){
        this.petServ= petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> quiSuisJe(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(petServ.getPetById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<String> addPet(@RequestBody PetDTO petDTO){
        Pet pet = petServ.createPet(petDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal créé");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PetResponseDTO> updDTOPet(@PathVariable long id,@RequestBody PetDTO petDTO){
        return ResponseEntity.status(HttpStatus.FOUND).body(petServ.updateDTOPet(id, petDTO));
    }
}
