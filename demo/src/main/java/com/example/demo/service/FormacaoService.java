package com.example.demo.service;

import com.example.demo.model.Curriculo;
import com.example.demo.model.Formacao;
import com.example.demo.repository.CurriculoRepository;
import com.example.demo.repository.FormacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class FormacaoService {

    private final FormacaoRepository formacaoRepository;
    private final CurriculoRepository curriculoRepository;

    @Autowired
    public FormacaoService(FormacaoRepository formacaoRepository, CurriculoRepository curriculoRepository) {
        this.formacaoRepository = formacaoRepository;
        this.curriculoRepository = curriculoRepository;
    }

    public Formacao createFormacao(Formacao formacao, UUID curriculoId) {
        Optional<Curriculo> optionalCurriculo = curriculoRepository.findById(curriculoId);
        if (optionalCurriculo.isPresent()) {
            Curriculo curriculo = optionalCurriculo.get();
            formacao.setCurriculo(curriculo);

            // Evitar duplicação ao verificar se a formação já está associada ao currículo
            List<Formacao> formacoesExistentes = formacaoRepository.findByCurriculoId(curriculoId);
            for (Formacao formacaoExistente : formacoesExistentes) {
                if (formacaoExistente.equals(formacao)) {
                    throw new IllegalArgumentException("Formação já existe para este currículo");
                }
            }

            return formacaoRepository.save(formacao);
        } else {
            return null; // Ou lançar uma exceção personalizada
        }
    }

    public Optional<Formacao> getFormacaoById(UUID id) {
        return formacaoRepository.findById(id);
    }

    public Formacao updateFormacao(Formacao formacao, UUID id) {
        Optional<Formacao> optionalFormacao = formacaoRepository.findById(id);
        if (optionalFormacao.isPresent()) {
            Formacao existingFormacao = optionalFormacao.get();
            existingFormacao.setTempoParaConcluirCurso(formacao.getTempoParaConcluirCurso());
            existingFormacao.setTipoGraduacao(formacao.getTipoGraduacao());
            return formacaoRepository.save(existingFormacao);
        } else {
            return null; // Ou lançar uma exceção personalizada
        }
    }

    public Boolean deleteFormacao(UUID id) {
        try {
            formacaoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

