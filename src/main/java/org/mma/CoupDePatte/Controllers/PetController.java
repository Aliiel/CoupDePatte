package org.mma.CoupDePatte.Controllers;

import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.DTO.PetResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Pet;
import org.mma.CoupDePatte.Services.PetServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/pets")
public class PetController {
    PetServices petServ;

    public PetController(PetServices petService){
        this.petServ= petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> quiSuisJe(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(petServ.getPetById(id));
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<Pet>> PetFilter(@RequestBody FilterDTO filter){
        return ResponseEntity.status(HttpStatus.FOUND).body(petServ.getPetByFilterSpec(filter));
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
