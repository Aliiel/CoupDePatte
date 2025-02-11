package org.mma.CoupDePatte.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genders")
public class GenderController {

    private final GenderServices genderServices;

    @Autowired
    public GenderController(GenderServices genderServices) {
        this.genderServices = genderServices;
    }

}
