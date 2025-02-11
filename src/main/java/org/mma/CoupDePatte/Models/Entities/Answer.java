package org.mma.CoupDePatte.Models.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "isTakeIn")
    private Boolean isTakeIn;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Advert advert;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
