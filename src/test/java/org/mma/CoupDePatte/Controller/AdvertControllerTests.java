package org.mma.CoupDePatte.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mma.CoupDePatte.Controllers.AdvertController;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Services.AdvertService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    void setUp() {
        advertDTO = new AdvertDTO(new Date(), "Test description", "photo.jpg", true, true, "test@example.com", null, null);
        advertResponseDTO = new AdvertResponseDTO(new Date(), "Test description", "photo.jpg", true, true, true, null, null, new Date(), new Date());
    }

    @Test
    void testGetAdvertById() {
        when(advertService.getDTOById(1L)).thenReturn(advertResponseDTO);  // ✅ Simulation correcte du service

        ResponseEntity<AdvertResponseDTO> response = advertController.getAdvertbyId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
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
