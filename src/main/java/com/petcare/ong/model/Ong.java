package com.petcare.ong.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ong")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
}
