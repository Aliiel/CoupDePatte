package org.mma.CoupDePatte.Models.MetaModels;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import org.mma.CoupDePatte.Models.Entities.Breed;
import org.mma.CoupDePatte.Models.Entities.Gender;
import org.mma.CoupDePatte.Models.Entities.Pet;

@StaticMetamodel(Pet.class)
public abstract class PetMetaModel {
    //on déclare les attributs de Pet pour faire le lien avec l'entity Pet
    public static volatile SingularAttribute<Pet, Long> id;
    public static volatile SingularAttribute<Pet, String> name;
    public static volatile SingularAttribute<Pet, String> behavior;
    public static volatile SingularAttribute<Pet, String> eyesColor;
    public static volatile SingularAttribute<Pet, String> coatColor;
    public static volatile SingularAttribute<Pet, Boolean> tattoo;
    public static volatile SingularAttribute<Pet, Boolean> identificationChip;
    public static volatile SingularAttribute<Pet, Gender> gender;
    public static volatile SingularAttribute<Pet, Breed> breed;
    //on déclare les mots clés qui seront utilisés pour chaque attribut dans la construction des requètes dynamiques
    public static final String ID = "id";
    public static final String NAME ="name" ;
    public static final String BEHAVIOR = "behavior";
    public static final String EYESCOLOR = "eyesColor";
    public static final String COATCOLOR = "coatColor";
    public static final String TATTOO = "tattoo";
    public static final String IDENTIFICATION = "identificationChip";
    public static final String GENDER = "gender";
    public static final String BREED = "breed";

    public PetMetaModel(){}

}
