package org.mma.CoupDePatte.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mma.CoupDePatte.Controllers.AdvertController;
import org.mma.CoupDePatte.Controllers.PetController;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Services.AdvertService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdvertServiceTests {

    @Mock
    private AdvertService advertService;

    @InjectMocks
    private AdvertController advertController;

    private AdvertDTO advertDTO;
    private AdvertResponseDTO advertResponseDTO;
    private Advert advert;
    private List<Advert> advertList;
    private FilterDTO filterDTO;

    @BeforeEach
    void setUp() {
        advertDTO = new AdvertDTO(new Date(), "Test description", "photo.jpg", true, true,
            "test@example.com", null, null);
        advertResponseDTO = new AdvertResponseDTO(new Date(), "Test description", "photo.jpg", true,
            true, true, null, null, new Date(), new Date());
        filterDTO = new FilterDTO(true, new CityDTO("Paris", "75000"), new Date(), "Chien",
            "Labrador", "Male", "Marron", "Noir", true
        );

        advert = Advert.builder()
            .id(1L)
            .creationDate(new Date())
            .eventDate(new Date())
            .updateDate(new Date())
            .description("Test description")
            .photoUrl("photo.jpg")
            .isActive(true)
            .isTakeIn(true)
            .isFound(true)
            .user(null)
            .pet(null)
            .city(null)
            .build();
    }

    @Test
    void testGetAdvertById_Service() {
        when(advertService.getDTOById(1L)).thenReturn(advertResponseDTO);
        AdvertResponseDTO response = advertService.getDTOById(1L);
        assertEquals(advertResponseDTO, response);
    }

    @Test
    void testCreateAdvert_Service() {
        when(advertService.createAdvert(any(AdvertDTO.class))).thenReturn("Annonce créée avec succès");
        String response = advertService.createAdvert(advertDTO);
        assertEquals("Annonce créée avec succès", response);
    }

    @Test
    void testGetAdvertById_Controller() {
        when(advertService.getDTOById(1L)).thenReturn(advertResponseDTO);
        ResponseEntity<AdvertResponseDTO> response = advertController.getAdvertbyId(1L);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(advertResponseDTO, response.getBody());
    }

    @Test
    void testCreateAdvert_Controller() {
        when(advertService.createAdvert(any(AdvertDTO.class))).thenReturn("Annonce créée avec succès");
        ResponseEntity<String> response = advertController.newAdvert(advertDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Annonce créée avec succès", response.getBody());
    }

    @Test
    void testFindGoodList_Empty() {
        ArrayList<Advert> adverts = new ArrayList<>();
        when(advertService.findGoodList(filterDTO)).thenReturn(adverts);

        ArrayList<Advert> result = advertService.findGoodList(filterDTO);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testFindGoodList_WithAdverts() {
        ArrayList<Advert> adverts = new ArrayList<>();
        adverts.add(new Advert());
        when(advertService.findGoodList(filterDTO)).thenReturn(adverts);

        ArrayList<Advert> result = advertService.findGoodList(filterDTO);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindGoodList_ThrowsException() {
        when(advertService.findGoodList(filterDTO)).thenThrow(new ResourceNotFoundException("Aucune annonce trouvée"));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            advertService.findGoodList(filterDTO);
        });

        assertEquals("Aucune annonce trouvée", exception.getMessage());
    }

    @Test
    void testGetByFilter_ThrowsException() {
        when(advertService.getByFilter(filterDTO)).thenThrow(new ResourceNotFoundException("Aucune annonce ne correspond à votre sélection"));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            advertService.getByFilter(filterDTO);
        });

        assertEquals("Aucune annonce ne correspond à votre sélection", exception.getMessage());
    }

    @Test
    void testUpdAdvActive() {
        doNothing().when(advertService).updAdvActive(1L);
        advertService.updAdvActive(1L);
        verify(advertService, times(1)).updAdvActive(1L);
    }

}
