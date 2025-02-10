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
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "email", length = 100)
    private String email;

    @Column (name = "phone", length = 20)
    private String phone;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "isTakeIn", nullable = false)
    private Boolean isTakeIn;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Advert advert;
}
