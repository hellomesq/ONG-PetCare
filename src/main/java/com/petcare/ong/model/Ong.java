package com.petcare.ong.model;

import jakarta.persistence.*;
import lombok.*; // ← IMPORTAÇÃO DO LOMBOK
import java.util.List;

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

    private String telefone;

    private String endereco;

    private String senha;

    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL)
    private List<Pet> pets;
}