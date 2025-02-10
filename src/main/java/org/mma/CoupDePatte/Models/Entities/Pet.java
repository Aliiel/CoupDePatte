package org.mma.CoupDePatte.Models.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "behavior", length = 200)
    private String behavior;

    @Column(name = "eyes_color", length = 30)
    private String eyesColor;

    @Column(name = "coat_color", nullable = false, length = 30)
    private String coatColor;

    @Column(name ="tattoo")
    private Boolean tattoo = false;

    @Column(name = "identification_chip")
    private Boolean identificationChip = false;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;

}
