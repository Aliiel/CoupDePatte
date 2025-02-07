package org.mma.CoupDePatte.Controller;

import org.mma.CoupDePatte.Controllers.PetController;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.DTO.PetResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Pet;
import org.mma.CoupDePatte.Services.PetServices;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


class TestPetController {
    private MockMvc mockMvc;
    @Mock
    private PetServices petServ;
    @InjectMocks
    private PetController petCtrl;

    private long petId;
    private PetResponseDTO petRDTO;
    private Pet pet;

    @Test
    void testGetById_Success() throws Exception{
        when(petServ.getPetById(petId)).thenReturn(petRDTO);

        mockMvc.perform(get("pet/{id}", petId))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.SpecieName").value("Chien"))
                .andExpect(jsonPath("$.genderName").value("Femelle"))
                .andExpect(jsonPath("$.Name").value("Violette"))
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
        when(petServ.createPet(any(PetDTO.class))).thenReturn(pet);

        mockMvc.perform(post("/pet/new")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("\"{\"name\":\"violette\",\"behavior\":\"tres calme\",\"eyesColor\":\"jaune\"," +
                            "\"coatColor\":\"marron et blanc\",\"tattoo\":\"false\",\"identificationChio\":\"true\"" +
                                    "\"genderId\":\"1\",\"breedId\":\"3\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$."))
        ;
    }

}
/*
    String coatColor,
    Boolean tattoo,
    Boolean identificationChip,
    long genderId,
    long breedId*/