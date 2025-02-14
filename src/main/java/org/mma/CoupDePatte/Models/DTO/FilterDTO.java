package org.mma.CoupDePatte.Models.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record FilterDTO(
        //Gestion des filtres choisis par l'utilisateur pour lui envoyer les annonces correspondant à son filtre
        @NotNull(message = "Merci de préciser le type d'annonces cherché")
        @Valid
        boolean isTrouve,
        @NotBlank(message = "Précisez à proximité de quelle ville et précisez le code postal")
        @Valid
        CityDTO cityDTO,
        @NotNull (message = "Précisez à partir de quelle date faire la recherche")
        @Valid
        Date eventDate,
        @NotBlank(message = "Sélectionnez une espèce d'animal")
        @Valid
        String specie,
        String breed,
        String gender,
        String eyes,
        String coat,
        boolean tattoo
) {
}
