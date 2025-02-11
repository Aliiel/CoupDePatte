package org.mma.CoupDePatte.Controllers;

import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Services.GenderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genders")
@RequiredArgsConstructor
public class GenderController {

    private final GenderService genderServices;

}
