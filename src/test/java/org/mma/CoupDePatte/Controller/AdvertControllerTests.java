package org.mma.CoupDePatte.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mma.CoupDePatte.Controllers.AdvertController;
import org.mma.CoupDePatte.Models.DTO.*;
import org.mma.CoupDePatte.Services.AdvertService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertControllerTests {

    @Mock
    private AdvertService advertService;

    @InjectMocks
    private AdvertController advertController;

    private AdvertDTO advertDTO;
    private AdvertResponseDTO advertResponseDTO;
    private MsgDTO msgDTO;

    @BeforeEach
    void setUp() {
        advertDTO = new AdvertDTO(new Date(),"testDescription",  "photo.jpg", true, true, "test@example.com", null, null);
        advertResponseDTO = new AdvertResponseDTO(new Date(), "Test description", "photo.jpg", true, true, true, null, null, new Date(), new Date());
        msgDTO = new MsgDTO("", "found@gmail.com", "0789125699", new Date(), true);
    }

    @Test
    void testGetAdvertById() {
        long id = 1L;
        when(advertService.getDTOById(id)).thenReturn(advertResponseDTO);

        ResponseEntity<AdvertResponseDTO> response = advertController.getAdvertbyId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testNewAdvert() {
        when(advertService.createAdvert(advertDTO)).thenReturn("Advert created successfully");

        ResponseEntity<String> response = advertController.newAdvert(advertDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Advert created successfully", response.getBody());
    }

    @Test
    void testUpdAdvert() {
        long id = 1L;
        when(advertService.updateAdvert(id, advertDTO)).thenReturn(advertResponseDTO);

        ResponseEntity<AdvertResponseDTO> response = advertController.updAdvert(id, advertDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdAdvActivation() {
        long id = 1L;
        doNothing().when(advertService).updAdvActive(id);

        ResponseEntity<Void> response = advertController.updAdvActivation(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdAdvUnActivation() {
        long id = 1L;
        doNothing().when(advertService).updAdvUnActive(id);

        ResponseEntity<Void> response = advertController.updAdvUnActivation(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testAddMsg() {
        long id = 1L;
        when(advertService.createMsg(id, msgDTO)).thenReturn("Message added successfully");

        ResponseEntity<String> response = advertController.addMsg(id, msgDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Message added successfully", response.getBody());
    }

    @Test
    void testAddAnswer() {
        long id = 1L;
        String email = "test@example.com";
        when(advertService.createAnswer(id, email, msgDTO)).thenReturn("Answer added successfully");

        ResponseEntity<String> response = advertController.addAnswer(id, email, msgDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Answer added successfully", response.getBody());
    }

    @Test
    void testUpdateAnswer() {
        long id = 1L;
        when(advertService.updAnswer(id, msgDTO)).thenReturn("Answer updated successfully");

        ResponseEntity<String> response = advertController.updateAnswer(id, msgDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Answer updated successfully", response.getBody());
    }

    @Test
    void testDisplayAnswer() {
        long id = 1L;
        ArrayList<MsgDTO> msgList = new ArrayList<>();
        msgList.add(msgDTO);
        when(advertService.searchAnswer(id)).thenReturn(msgList);

        ResponseEntity<ArrayList<MsgDTO>> response = advertController.displayAnswer(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
}
