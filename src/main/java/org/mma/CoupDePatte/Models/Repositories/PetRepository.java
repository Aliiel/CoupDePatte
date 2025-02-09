package org.mma.CoupDePatte.Models.Repositories;

import jakarta.annotation.Nullable;
import org.mma.CoupDePatte.Models.Entities.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
    Optional<Pet> findById(Long id);

    //Gestion du filtre des annonces avec requêtes dynamiques
    ArrayList<Pet> findAll (@Nullable Specification<Pet> var1);

    //Gestion du filtre des annonces avec requêtes fixes (pas utilisées dans ce programme)
    /*
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndEyesColorAndTattooTrue(ArrayList<Breed> breeds, Breed breed, Gender gender, String eyes);
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndEyesColorAndTattooFalse(ArrayList<Breed> breeds, Breed breed, Gender gender, String eyes);
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, Breed breed, Gender gender, String coat);
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, Breed breed, Gender gender, String coat);
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndTattooTrue(ArrayList<Breed> breeds, Breed breed, Gender gender);
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndTattooFalse(ArrayList<Breed> breeds, Breed breed, Gender gender);
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndEyesColorAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, Breed breed, Gender gender,String eyes, String coat);
    ArrayList<Pet> findByBreedInAndBreedAndGenderAndEyesColorAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, Breed breed, Gender gender,String eyes, String coat);

    ArrayList<Pet> findByBreedInAndBreedAndEyesColorAndTattooTrue(ArrayList<Breed> breeds, Breed breed, String eyes);
    ArrayList<Pet> findByBreedInAndBreedAndEyesColorAndTattooFalse(ArrayList<Breed> breeds, Breed breed, String eyes);
    ArrayList<Pet> findByBreedInAndBreedAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, Breed breed, String coat);
    ArrayList<Pet> findByBreedInAndBreedAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, Breed breed, String coat);
    ArrayList<Pet> findByBreedInAndBreedAndTattooTrue(ArrayList<Breed> breeds, Breed breed);
    ArrayList<Pet> findByBreedInAndBreedAndTattooFalse(ArrayList<Breed> breeds, Breed breed);
    ArrayList<Pet> findByBreedInAndBreedAndEyesColorAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, Breed breed, String eyes, String coat);
    ArrayList<Pet> findByBreedInAndBreedAndEyesColorAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, Breed breed, String eyes, String coat);

    ArrayList<Pet> findByBreedInAndGenderAndEyesColorAndTattooTrue(ArrayList<Breed> breeds, Gender gender, String eyes);
    ArrayList<Pet> findByBreedInAndGenderAndEyesColorAndTattooFalse(ArrayList<Breed> breeds, Gender gender, String eyes);
    ArrayList<Pet> findByBreedInAndGenderAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, Gender gender, String coat);
    ArrayList<Pet> findByBreedInAndGenderAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, Gender gender, String coat);
    ArrayList<Pet> findByBreedInAndGenderAndTattooTrue(ArrayList<Breed> breeds, Gender gender);
    ArrayList<Pet> findByBreedInAndGenderAndTattooFalse(ArrayList<Breed> breeds, Gender gender);
    ArrayList<Pet> findByBreedInAndGenderAndEyesColorAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, Gender gender,String eyes, String coat);
    ArrayList<Pet> findByBreedInAndGenderAndEyesColorAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, Gender gender,String eyes, String coat);

    ArrayList<Pet> findByBreedInAndEyesColorAndTattooTrue(ArrayList<Breed> breeds, String eyes);
    ArrayList<Pet> findByBreedInAndEyesColorAndTattooFalse(ArrayList<Breed> breeds, String eyes);
    ArrayList<Pet> findByBreedInAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, String coat);
    ArrayList<Pet> findByBreedInAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, String coat);
    ArrayList<Pet> findByBreedInAndTattooTrue(ArrayList<Breed> breeds);
    ArrayList<Pet> findByBreedInAndTattooFalse(ArrayList<Breed> breeds);
    ArrayList<Pet> findByBreedInAndEyesColorAndCoatColorAndTattooTrue(ArrayList<Breed> breeds, String eyes, String coat);
    ArrayList<Pet> findByBreedInAndEyesColorAndCoatColorAndTattooFalse(ArrayList<Breed> breeds, String eyes, String coat);

     */

}


