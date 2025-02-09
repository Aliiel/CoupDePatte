package org.mma.CoupDePatte.Controllers;

import org.mma.CoupDePatte.Models.Entities.Gender;
import org.mma.CoupDePatte.Services.GenderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/genders")
public class GenderController {

    private final GenderServices genderServices;

    @Autowired
    public GenderController(GenderServices genderServices) {
        this.genderServices = genderServices;
    }

}
