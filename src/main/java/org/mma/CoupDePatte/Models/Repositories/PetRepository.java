package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findById(Long id);

    //Gestion du filtre des annonces

    ArrayList<Pet> findBySpecieAndBreedAndGenderAndEyesColorAndTattooTrue(Specie specie, Breed breed, Gender gender, String eyes);
    ArrayList<Pet> findBySpecieAndBreedAndGenderAndEyesColorAndTattooFalse(Specie specie, Breed breed, Gender gender, String eyes);
    ArrayList<Pet> findBySpecieAndBreedAndGenderAndCoatColorAndTattooTrue(Specie specie, Breed breed, Gender gender, String coat);
    ArrayList<Pet> findBySpecieAndBreedAndGenderAndCoatColorAndTattooFalse(Specie specie, Breed breed, Gender gender, String coat);
    ArrayList<Pet> findBySpecieAndBreedAndGenderAndTattooTrue(Specie specie, Breed breed, Gender gender);
    ArrayList<Pet> findBySpecieAndBreedAndGenderAndTattooFalse(Specie specie, Breed breed, Gender gender);
    ArrayList<Pet> findBySpecieAndBreedAndGenderAndEyesColorAndCoatColorAndTattooTrue(Specie specie, Breed breed, Gender gender,String eyes, String coat);
    ArrayList<Pet> findBySpecieAndBreedAndGenderAndEyesColorAndCoatColorAndTattooFalse(Specie specie, Breed breed, Gender gender,String eyes, String coat);

    ArrayList<Pet> findBySpecieAndBreedAndEyesColorAndTattooTrue(Specie specie, Breed breed, String eyes);
    ArrayList<Pet> findBySpecieAndBreedAndEyesColorAndTattooFalse(Specie specie, Breed breed, String eyes);
    ArrayList<Pet> findBySpecieAndBreedAndCoatColorAndTattooTrue(Specie specie, Breed breed, String coat);
    ArrayList<Pet> findBySpecieAndBreedAndCoatColorAndTattooFalse(Specie specie, Breed breed, String coat);
    ArrayList<Pet> findBySpecieAndBreedAndTattooTrue(Specie specie, Breed breed);
    ArrayList<Pet> findBySpecieAndBreedAndTattooFalse(Specie specie, Breed breed);
    ArrayList<Pet> findBySpecieAndBreedAndEyesColorAndCoatColorAndTattooTrue(Specie specie, Breed breed, String eyes, String coat);
    ArrayList<Pet> findBySpecieAndBreedAndEyesColorAndCoatColorAndTattooFalse(Specie specie, Breed breed, String eyes, String coat);

    ArrayList<Pet> findBySpecieAndGenderAndEyesColorAndTattooTrue(Specie specie, Gender gender, String eyes);
    ArrayList<Pet> findBySpecieAndGenderAndEyesColorAndTattooFalse(Specie specie, Gender gender, String eyes);
    ArrayList<Pet> findBySpecieAndGenderAndCoatColorAndTattooTrue(Specie specie, Gender gender, String coat);
    ArrayList<Pet> findBySpecieAndGenderAndCoatColorAndTattooFalse(Specie specie, Gender gender, String coat);
    ArrayList<Pet> findBySpecieAndGenderAndTattooTrue(Specie specie, Gender gender);
    ArrayList<Pet> findBySpecieAndGenderAndTattooFalse(Specie specie, Gender gender);
    ArrayList<Pet> findBySpecieAndGenderAndEyesColorAndCoatColorAndTattooTrue(Specie specie, Gender gender,String eyes, String coat);
    ArrayList<Pet> findBySpecieAndGenderAndEyesColorAndCoatColorAndTattooFalse(Specie specie, Gender gender,String eyes, String coat);

    ArrayList<Pet> findBySpecieAndEyesColorAndTattooTrue(Specie specie, String eyes);
    ArrayList<Pet> findBySpecieAndEyesColorAndTattooFalse(Specie specie, String eyes);
    ArrayList<Pet> findBySpecieAndCoatColorAndTattooTrue(Specie specie, String coat);
    ArrayList<Pet> findBySpecieAndCoatColorAndTattooFalse(Specie specie, String coat);
    ArrayList<Pet> findBySpecieAndTattooTrue(Specie specie);
    ArrayList<Pet> findBySpecieAndTattooFalse(Specie specie);
    ArrayList<Pet> findBySpecieAndEyesColorAndCoatColorAndTattooTrue(Specie specie, String eyes, String coat);
    ArrayList<Pet> findBySpecieAndEyesColorAndCoatColorAndTattooFalse(Specie specie, String eyes, String coat);

}


