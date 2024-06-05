package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Curriculo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profissao;

    @Column(nullable = false)
    private String curso;

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Formacao> formacoes;
}
