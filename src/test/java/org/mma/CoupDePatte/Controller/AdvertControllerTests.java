package org.mma.CoupDePatte.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mma.CoupDePatte.Controllers.AdvertController;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Services.AdvertService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AdvertControllerTests {

    @Mock
    private AdvertService advertService;

    @InjectMocks
    private AdvertController advertController;

    private AdvertDTO advertDTO;
    private AdvertResponseDTO advertResponseDTO;
    private FilterDTO filterDTO;

    @BeforeEach
    void setUp() {
        advertDTO = new AdvertDTO(new Date(), "Test description", "photo.jpg", true, true, "test@example.com", null, null);
        filterDTO = new FilterDTO(true, new CityDTO("Paris", "75000"), new Date(), "Chien", // specie
            "Labrador", // breed (facultatif, peut être null)
            "Male", // gender (facultatif, peut être null)
            "Marron", // eyes (facultatif, peut être null)
            "Noir", // coat (facultatif, peut être null)
            true // tattoo (facultatif, peut être null)
        );
    }

    @Test
    void testGetAdvertById() {
        when(advertService.getDTOById(1L)).thenReturn(advertResponseDTO);

        ResponseEntity<AdvertResponseDTO> response = advertController.getAdvertbyId(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(advertResponseDTO, response.getBody());
    }

    @Test
    void testCreateAdvert() {
        when(advertService.createAdvert(any(AdvertDTO.class))).thenReturn("Annonce créée avec succès");

        ResponseEntity<String> response = advertController.newAdvert(advertDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Annonce créée avec succès", response.getBody());
    }

    @Test
    void testGetAdvertById_NotFound() {
        when(advertService.getDTOById(1L)).thenThrow(new ResourceNotFoundException("Annonce non trouvée"));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            advertController.getAdvertbyId(1L);
        });

        assertEquals("Annonce non trouvée", exception.getMessage());
    }

    @Test
    void testGetAdvertsByFilter_NotFound() {
        when(advertService.getByFilter(any(FilterDTO.class))).thenThrow(new ResourceNotFoundException("Aucune annonce ne correspond à votre sélection"));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            advertController.getAdvertsByFilter(filterDTO);
        });

        assertEquals("Aucune annonce ne correspond à votre sélection", exception.getMessage());
    }

}
