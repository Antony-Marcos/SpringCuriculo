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
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    private final FormacaoRepository formacaoRepository;

    @Autowired
    public CurriculoService(CurriculoRepository curriculoRepository, FormacaoRepository formacaoRepository) {
        this.curriculoRepository = curriculoRepository;
        this.formacaoRepository = formacaoRepository;
    }

    public Curriculo createCurriculo(Curriculo curriculo) {
        return curriculoRepository.save(curriculo);
    }

    public List<Curriculo> encontrarCurriculo() {
        return curriculoRepository.findAll();
    }

    public Optional<Curriculo> curriculoId(UUID id) {
        return curriculoRepository.findById(id);
    }

    public Curriculo atualizarCurriculo(Curriculo curriculoAtualizado, UUID id) {
        Optional<Curriculo> optionalCurriculo = curriculoRepository.findById(id);
        if (optionalCurriculo.isPresent()) {
            Curriculo curriculo = optionalCurriculo.get();
            curriculo.setName(curriculoAtualizado.getName());
            curriculo.setProfissao(curriculoAtualizado.getProfissao());
            curriculo.setCurso(curriculoAtualizado.getCurso());
            return curriculoRepository.save(curriculo);
        } else {
            return null;
        }
    }

    public Boolean deletarCurriculo(UUID id) {
        try {
            curriculoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Curriculo addFormacaoToCurriculo(Formacao formacao, UUID curriculoId) {
        Optional<Curriculo> optionalCurriculo = curriculoRepository.findById(curriculoId);
        if (optionalCurriculo.isPresent()) {
            Curriculo curriculo = optionalCurriculo.get();
            formacao.setCurriculo(curriculo);
            curriculo.getFormacoes().add(formacao);
            curriculoRepository.save(curriculo);
            return curriculo;
        } else {
            return null;
        }
    }

    public Curriculo removeFormacaoFromCurriculo(UUID formacaoId, UUID curriculoId) {
        Optional<Curriculo> optionalCurriculo = curriculoRepository.findById(curriculoId);
        if (optionalCurriculo.isPresent()) {
            Curriculo curriculo = optionalCurriculo.get();
            Formacao formacao = formacaoRepository.findById(formacaoId).orElse(null);
            if (formacao != null) {
                curriculo.getFormacoes().remove(formacao);
                curriculoRepository.save(curriculo);
            }
            return curriculo;
        } else {
            return null;
        }
    }
}
