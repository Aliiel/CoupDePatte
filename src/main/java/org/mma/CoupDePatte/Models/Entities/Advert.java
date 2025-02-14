package org.mma.CoupDePatte.Models.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "advert")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "event_date", nullable = false)
    private Date eventDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Column(name = "isTakeIn")
    private Boolean isTakeIn;

    @Column(name = "isFound", nullable = false)
    private Boolean isFound;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "advert", orphanRemoval = true)
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "advert", orphanRemoval = true)
    private Set<Answer> answers = new LinkedHashSet<>();
}
