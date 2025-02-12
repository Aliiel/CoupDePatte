package org.mma.CoupDePatte.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mma.CoupDePatte.Controllers.PetController;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.DTO.PetResponseDTO;
import org.mma.CoupDePatte.Models.Entities.*;
import org.mma.CoupDePatte.Services.PetService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.toDate;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class TestPetController {
    private MockMvc mockMvc;
    @Mock //bloque le service pour les tests
    private PetService petServ;
    @InjectMocks //indique que c'est le controller que l'on simule
    private PetController petCtrl;

    private long petId;
    private long petIdKO;
    private PetResponseDTO petRDTO;
    private Pet pet1;
    private Pet pet2;
    private Pet pet3;
    private FilterDTO filter1;
    private FilterDTO filter2;
    private FilterDTO filterChien;
    private Date dateDTO;
    private ArrayList<Pet> lstPetOk= new ArrayList<>();
    private ArrayList<Pet> lstPetChien= new ArrayList<>();
    private CityDTO cityDTO1;
    private CityDTO cityDTO2;
    private Gender femelle;
    private Gender male;
    private Breed brchat;
    private Breed brchien;
    private Specie chien;
    private Specie chat;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat frmDate = new SimpleDateFormat("yyyy-MM-dd");
        dateDTO= new SimpleDateFormat("dd/MM/2025").parse("09/02/2025");

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
       // lstPetChien.add(pet1);
        //lstPetChien.add(pet3);
        petId = 1L;
        petIdKO=10L;
        petRDTO=new PetResponseDTO("chien", "teckel", "femelle", "violette",
                "tres calme", "jaune", "marron et blanc", false,true);
        cityDTO1=new CityDTO("Roubaix","59100");
        cityDTO2=new CityDTO("Paris","75000");
        filter1 = new FilterDTO(true,cityDTO1,dateDTO,"chien","teckel",
                "femelle","jaune","marron et blanc",false);
        filter2 = new FilterDTO(true,cityDTO2,dateDTO,"chien","husky",
                "femelle","jaune","marron et blanc",false);

    }


    @Test
    void testGetByFilterSpec_Success() throws Exception{
        when(petServ.getPetByFilterSpec(filter1)).thenReturn(lstPetOk);
        String pathATester = "/pets/";
        String inputDTO = "{\"isTrouve\":\"true\",\"cityDTO\":{\"name\":\"Roubaix\",\"zipCode\":\"59100\"}," +
                "\"eventDate\":\"2025-02-09\",\"breed\":\"teckel\",\"eyesColor\":\"jaune\"," +
                "\"coatColor\":\"marron et blanc\",\"tattoo\":\"false\"}";

        MockHttpServletRequestBuilder req = get(pathATester).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(inputDTO);

        this.mockMvc.perform(req).andExpect(status().isFound())
                .andExpect(jsonPath("$..Name").value("violette"));

    }

    @Test
    void testGetByFilterSpec_NotSuccess() throws Exception{
        when(petServ.getPetByFilterSpec(filter2)).thenReturn(lstPetChien);
        String pathATester = "/pets/";
        String inputDTO = "{\"isTrouve\":\"true\",\"cityDTO\":{\"name\":\"Roubaix\",\"zipCode\":\"59100\"}," +
                "\"eventDate\":\"2025-02-09\",\"breed\":\"teckel\",\"eyesColor\":\"jaune\"," +
                "\"coatColor\":\"marron et blanc\",\"tattoo\":\"false\"}";

        MockHttpServletRequestBuilder req =get(pathATester).contentType(MediaType.APPLICATION_JSON)
                        .content(inputDTO);

        this.mockMvc.perform(req).andExpect(status().isFound())
                .andExpect(jsonPath("$..Name").isEmpty());

    }

    @Test
    void testGetById_Success() throws Exception{
        when(petServ.getPetById(petId)).thenReturn(petRDTO);
        String pathATester = "/pets/{id}";

        MockHttpServletRequestBuilder req =get(pathATester,petId);

        this.mockMvc.perform(req).andExpect(status().isFound())
                .andExpect(jsonPath("$.SpecieName").value("chien"))
                .andExpect(jsonPath("$.genderName").value("femelle"))
                .andExpect(jsonPath("$.name").value("violette"))
                .andExpect(jsonPath("$.behavior").value("tres calme"))
                .andExpect(jsonPath("$.eyesColor").value("jaune"))
                .andExpect(jsonPath("$.coatColor").value("marron et blanc"))
                .andExpect(jsonPath("$.tattoo").value(false))
                .andExpect(jsonPath("$.identificationChip").value(true));

    }

    @Test
    void testGetById_notSuccess() throws Exception{
        doThrow(new ResourceNotFoundException("Animal avec ID " + Long.toString(petIdKO) + " non trouvé"))
                .when(petServ)
                .getPetById(petIdKO);

        mockMvc.perform(get("/pets/{id}", petIdKO))
                .andExpect(status().isNotFound());

    }

    @Test
    void testCreatePet() throws Exception{
        when(petServ.createPet(any(PetDTO.class))).thenReturn(pet1);

        String pathATester = "/pets/new";
        String inputDTO = "{\"name\":\"violette\",\"behavior\":\"tres calme\",\"eyesColor\":\"jaune\"," +
                "\"coatColor\":\"marron et blanc\",\"tattoo\":\"false\",\"identificationChip\":\"true\"," +
                "\"genderId\":\"1\",\"breedId\":\"3\"}";
        MockHttpServletRequestBuilder req =get(pathATester).contentType(MediaType.APPLICATION_JSON)
                .content(inputDTO);

        this.mockMvc.perform(req).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("violette"))
                .andExpect(jsonPath("$.behavior").value("tres calme"))
                .andExpect(jsonPath("$.eyesColor").value("jaune"))
                .andExpect(jsonPath("$.coatColor").value("marron et blanc"))
                .andExpect(jsonPath("$.tattoo").value(false))
                .andExpect(jsonPath("$.identificationChip").value(true))
                .andExpect(jsonPath("$.gender").value(femelle))
                .andExpect(jsonPath("$.breed").value(brchien))
        ;

    }

    @Test
    void testUpdatePet_Success() throws Exception{
        when(petServ.updatePet(petId,any(PetDTO.class))).thenReturn(pet1);

        String pathATester = "/pets/update/{id}";
        String inputDTO = "{\"name\":\"null\",\"behavior\":\"tres calme et peureuse\",\"eyesColor\":\"null\"," +
                "\"coatColor\":\"null\",\"tattoo\":\"null\",\"identificationChip\":\"null\"," +
                "\"genderId\":\"2\",\"breedId\":\"3\"}";

        MockHttpServletRequestBuilder req =get(pathATester,petId).contentType(MediaType.APPLICATION_JSON)
                .content(inputDTO);

        this.mockMvc.perform(req).andExpect(status().isFound())
                .andExpect(jsonPath("$.name").value("violette"))
                .andExpect(jsonPath("$.behavior").value("tres calme et peureuse"))
                .andExpect(jsonPath("$.eyesColor").value("jaune"))
                .andExpect(jsonPath("$.coatColor").value("marron et blanc"))
                .andExpect(jsonPath("$.tattoo").value(false))
                .andExpect(jsonPath("$.identificationChip").value(true))
                .andExpect(jsonPath("$.gender").value(male))
                .andExpect(jsonPath("$.breed").value(brchien))
        ;

    }

    @Test
    void testUpdatePet_notSucces() throws Exception{
        when(petServ.updatePet(petIdKO,any(PetDTO.class)))
                .thenThrow(new ResourceNotFoundException("Animal avec ID " + Long.toString(petIdKO) + " non trouvé"));
        String pathATester = "/pets/update/{id}";
        String inputDTO = "{\"name\":\"null\",\"behavior\":\"tres calme et peureuse\",\"eyesColor\":\"null\"," +
                "\"coatColor\":\"null\",\"tattoo\":\"null\",\"identificationChip\":\"null\"," +
                "\"genderId\":\"null\",\"breedId\":\"null\"}";

        MockHttpServletRequestBuilder req =get(pathATester).contentType(MediaType.APPLICATION_JSON)
                .content(inputDTO);

        this.mockMvc.perform(req).andExpect(status().isNotFound());

    }

}
