package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.UUID;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Formacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String tempoParaConcluirCurso;

    @Column(nullable = false)
    private String tipoGraduacao;

    @ManyToOne
    @JsonBackReference
    private Curriculo curriculo;
}
