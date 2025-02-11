package org.mma.CoupDePatte.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.mma.CoupDePatte.Controllers.PetController;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.DTO.PetResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Breed;
import org.mma.CoupDePatte.Models.Entities.Gender;
import org.mma.CoupDePatte.Models.Entities.Pet;
import org.mma.CoupDePatte.Models.Entities.Specie;
import org.mma.CoupDePatte.Services.PetService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


class TestPetController {
    private MockMvc mockMvc;
    @Mock
    private PetService petServ;
    @InjectMocks
    private PetController petCtrl;

    private long petId;
    private PetResponseDTO petRDTO;
    private Pet pet1;
    private Pet pet2;
    private Pet pet3;
    private FilterDTO filter1;
    private FilterDTO filter2;
    private FilterDTO filterChien;
    private ArrayList<Pet> lstPetOk;
    private ArrayList<Pet> lstPetChien;
    private Gender femelle;
    private Gender male;
    private Breed brchat;
    private Breed brchien;
    private Specie chien;
    private Specie chat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petCtrl).build();

        //initialisation des objets utiles pour les tests
        chat= new Specie(1L,"chat");
        chien = new Specie(2L,"chien");
        femelle = new Gender(1L, "femelle");
        male = new Gender(2L, "mâle");
        brchien = new Breed(3L, "teckel",chien);
        brchat = new Breed(3L, "main coon",chat);
        pet1 = new Pet(1L,"violette", "tres calme", "jaune", "marron et blanc",
                false,true,femelle, brchien);
        pet2 = new Pet(2L,"mimi", "caline", "vert", "écaille",
                false,true,femelle, brchat);
        pet3 = new Pet(3L,"sarrazin", "sportif et timide", "marron", "noir",
                false,true,male, brchien);
        lstPetOk.add(pet1);
        lstPetChien.add(pet1);
        lstPetChien.add(pet3);
        petId = 1L;
        petRDTO=new PetResponseDTO("chien", "teckel", "femelle", "violette",
                "tres calme", "jaune", "marron et blanc", false,true);
        filter1 = new FilterDTO(true,"Paris",new Date(),"chien","teckel",
                "femelle","jaune","marron et blanc",false);
        filter2 = new FilterDTO(true,"Paris",new Date(),"chien","husky",
                "femelle","jaune","marron et blanc",false);

    }

    @Test
    void testGetByFilterSpec_Success() throws Exception{
        when(petServ.getPetByFilterSpec(filter1)).thenReturn(lstPetOk);

        mockMvc.perform(get("pet/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"{\"isTrouve\":\"true\",\"town\":\"Paris\",\"eventDate\":\"2025-02-09\"," +
                        "\"breed\":\"teckel\",\"eyesColor\":\"jaune\",\"coatColor\":\"marron et blanc\"," +
                        "\"tattoo\":\"false\"}\""))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.Name").value("violette"))
                .andExpect(jsonPath("$.behavior").value("très calme"))
                .andExpect(jsonPath("$.eyescolor").value("jaune"))
                .andExpect(jsonPath("$.coatcolor").value("marron et blanc"))
                .andExpect(jsonPath("$.tattoo").value(false))
                .andExpect(jsonPath("$.identificationChip").value(true))
                .andExpect(jsonPath("$.gender").value(femelle))
                .andExpect(jsonPath("$.breed").value(brchien))
        ;
    }

    @Test
    void testGetByFilterSpec_NotSuccess() throws Exception{
        when(petServ.getPetByFilterSpec(filter2)).thenReturn(lstPetChien);

        mockMvc.perform(get("pet/", petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"{\"isTrouve\":\"true\",\"town\":\"Paris\",\"eventDate\":\"2025-02-09\"," +
                                "\"breed\":\"husky\",\"eyesColor\":\"bleu\",\"coatColor\":\"marron et blanc\"," +
                                "\"tattoo\":\"false\"}\""))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetById_Success() throws Exception{
        when(petServ.getPetById(petId)).thenReturn(petRDTO);

        mockMvc.perform(get("pet/{id}", petId))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.SpecieName").value("chien"))
                .andExpect(jsonPath("$.genderName").value("femelle"))
                .andExpect(jsonPath("$.Name").value("violette"))
                .andExpect(jsonPath("$.behavior").value("très calme"))
                .andExpect(jsonPath("$.eyescolor").value("jaune"))
                .andExpect(jsonPath("$.coatcolor").value("marron et blanc"))
                .andExpect(jsonPath("$.tattoo").value(false))
                .andExpect(jsonPath("$.identificationChip").value(true))
        ;
    }

    @Test
    void testGetById_notSuccess() throws Exception{
        when(petServ.getPetById(petId)).thenThrow(new ResourceNotFoundException("Animal avec ID " + Long.toString(petId) + " non trouvé"));

        mockMvc.perform(get("pet/{id}", petId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePet() throws Exception{
        when(petServ.createPet(any(PetDTO.class))).thenReturn(pet1);

        mockMvc.perform(post("/pet/new")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("\"{\"name\":\"violette\",\"behavior\":\"tres calme\",\"eyesColor\":\"jaune\"," +
                            "\"coatColor\":\"marron et blanc\",\"tattoo\":\"false\",\"identificationChio\":\"true\"" +
                                    "\"genderId\":\"1\",\"breedId\":\"3\"}\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name").value("violette"))
                .andExpect(jsonPath("$.behavior").value("très calme"))
                .andExpect(jsonPath("$.eyescolor").value("jaune"))
                .andExpect(jsonPath("$.coatcolor").value("marron et blanc"))
                .andExpect(jsonPath("$.tattoo").value(false))
                .andExpect(jsonPath("$.identificationChip").value(true))
                .andExpect(jsonPath("$.gender").value(femelle))
                .andExpect(jsonPath("$.breed").value(brchien))
        ;
    }

}
