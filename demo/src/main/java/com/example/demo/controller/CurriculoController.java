package com.example.demo.controller;

import com.example.demo.model.Curriculo;
import com.example.demo.service.CurriculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/curriculos")
public class CurriculoController {

    private final CurriculoService curriculoService;

    @Autowired
    public CurriculoController(CurriculoService curriculoService) {
        this.curriculoService = curriculoService;
    }

    @PostMapping
    public ResponseEntity<Curriculo> createCurriculo(@RequestBody Curriculo curriculo) {
        Curriculo createdCurriculo = curriculoService.createCurriculo(curriculo);
        return ResponseEntity.ok(createdCurriculo);
    }

    @GetMapping
    public ResponseEntity<List<Curriculo>> getAllCurriculos() {
        List<Curriculo> curriculos = curriculoService.encontrarCurriculo();
        return ResponseEntity.ok(curriculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Curriculo>> getCurriculoById(@PathVariable UUID id) {
        Optional<Curriculo> curriculo = curriculoService.curriculoId(id);
        if (curriculo.isPresent()) {
            return ResponseEntity.ok(curriculo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curriculo> updateCurriculo(@RequestBody Curriculo curriculo, @PathVariable UUID id) {
        Curriculo updatedCurriculo = curriculoService.atualizarCurriculo(curriculo, id);
        return ResponseEntity.ok(updatedCurriculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurriculo(@PathVariable UUID id) {
        Boolean isDeleted = curriculoService.deletarCurriculo(id);
        if (isDeleted) {
            String message = "O currículo com o ID " + id + " foi deletado com sucesso.";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
