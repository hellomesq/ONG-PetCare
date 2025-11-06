package com.petcare.ong.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pet")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especie;   // cachorro, gato etc.
    private String raca;
    private Integer idade;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "ong_id")  // chave estrangeira
    private Ong ong;
}
