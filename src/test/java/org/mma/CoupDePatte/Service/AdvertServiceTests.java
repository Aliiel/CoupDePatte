package org.mma.CoupDePatte.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mma.CoupDePatte.Controllers.AdvertController;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
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

@ExtendWith(MockitoExtension.class)
public class AdvertServiceTests {

    @Mock
    private AdvertService advertService;

    @InjectMocks
    private AdvertController advertController;

    private AdvertDTO advertDTO;
    private AdvertResponseDTO advertResponseDTO;

    @BeforeEach
    void setUp() {
        advertDTO = new AdvertDTO(new Date(), "Test description", "photo.jpg", true, true, "test@example.com", null, null);
        advertResponseDTO = new AdvertResponseDTO(new Date(), "Test description", "photo.jpg", true, true, true, null, null, new Date(), new Date());
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
}

