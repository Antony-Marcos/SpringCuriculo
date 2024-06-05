package com.example.demo.controller;

import com.example.demo.model.Formacao;
import com.example.demo.service.FormacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/formacoes")
public class FormacaoController {

    private final FormacaoService formacaoService;

    @Autowired
    public FormacaoController(FormacaoService formacaoService) {
        this.formacaoService = formacaoService;
    }

    @PostMapping("/{curriculoId}")
    public ResponseEntity<Formacao> createFormacao(@RequestBody Formacao formacao, @PathVariable UUID curriculoId) {
        Formacao createdFormacao = formacaoService.createFormacao(formacao, curriculoId);
        if (createdFormacao != null) {
            return ResponseEntity.ok(createdFormacao);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Formacao>> getFormacaoById(@PathVariable UUID id) {
        Optional<Formacao> formacao = formacaoService.getFormacaoById(id);
        if (formacao.isPresent()) {
            return ResponseEntity.ok(formacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formacao> updateFormacao(@RequestBody Formacao formacao, @PathVariable UUID id) {
        Formacao updatedFormacao = formacaoService.updateFormacao(formacao, id);
        return ResponseEntity.ok(updatedFormacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFormacao(@PathVariable UUID id) {
        Boolean isDeleted = formacaoService.deleteFormacao(id);
        if (isDeleted) {
            String message = "A formação com o ID " + id + " foi deletada com sucesso.";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
