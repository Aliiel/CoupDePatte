package org.mma.CoupDePatte.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mma.CoupDePatte.Controllers.AdvertController;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Services.AdvertService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AdvertServiceTests {

    @Mock
    private AdvertService advertService;

    @InjectMocks
    private AdvertController advertController;

    private AdvertDTO advertDTO;
    private AdvertResponseDTO advertResponseDTO;
    private Advert advert;

    @BeforeEach
    void setUp() {
        advertDTO = new AdvertDTO(new Date(), "Test description", "photo.jpg", true, true, "test@example.com", null, null);
        advertResponseDTO = new AdvertResponseDTO(new Date(), "Test description", "photo.jpg", true, true, true, null, null, new Date(), new Date());

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
}
