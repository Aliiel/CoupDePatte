package org.mma.CoupDePatte.Models.Specifications;

import org.mma.CoupDePatte.Models.Entities.Breed;
import org.mma.CoupDePatte.Models.Entities.Gender;
import org.mma.CoupDePatte.Models.Entities.Pet;
import org.mma.CoupDePatte.Models.MetaModels.PetMetaModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PetSpecification {
    //pas utilisé dans le programme.
    public static Specification<Pet> eyesLike(String eyes){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.like(root.get(PetMetaModel.eyesColor),"%"+eyes+"%");
    }
    public static Specification<Pet> eyesEqual(String eyes){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.equal(root.get(PetMetaModel.eyesColor),eyes);
    }
    public static Specification<Pet> coatLike(String coat){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.like(root.get(PetMetaModel.coatColor),"%"+coat+"%");
    }
    public static Specification<Pet> coatEqual(String coat){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.equal(root.get(PetMetaModel.coatColor),coat);
    }
    public static Specification<Pet> tattooFalse(){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.isFalse(root.get(PetMetaModel.tattoo));
    }
    public static Specification<Pet> tattooTrue(){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.isTrue(root.get(PetMetaModel.tattoo));
    }
    public static Specification<Pet> genderEqual(Gender gender){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.equal(root.get(PetMetaModel.gender),gender);
    }
    public static Specification<Pet> breedEqual(Breed breed){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.equal(root.get(PetMetaModel.breed),breed);
    }
    public static Specification<Pet> breedIn(ArrayList<Breed> lstBreed){
        return (root,criteriaQuery,criteriaBuilder)->
                criteriaBuilder.in(root.get(PetMetaModel.breed).in(lstBreed));
    }
}
