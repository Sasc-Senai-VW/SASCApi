package com.senai.apisasc.controllers;

import com.senai.apisasc.dtos.SetorDto;
import com.senai.apisasc.models.SetorModel;
import com.senai.apisasc.repositories.SetorRepository;
import com.senai.apisasc.repositories.UnidadeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/setor", produces = {"application/json"})
public class SetorController {
    @Autowired
    SetorRepository setorRepository;

    @Autowired
    UnidadeRepository unidadeRepository;

    @GetMapping
    public ResponseEntity<List<SetorModel>> listarSetores() {
        return ResponseEntity.status(HttpStatus.OK).body(setorRepository.findAll());
    }

    @GetMapping("/{idSetor}")
    public ResponseEntity<Object> exibirSetores(@PathVariable(value = "idSetor")UUID id) {
        Optional<SetorModel> setorBuscado = setorRepository.findById(id);

        if (setorBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setor nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(setorBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarSetor(@RequestBody @Valid SetorDto setorDto) {
        SetorModel setor = new SetorModel();
        BeanUtils.copyProperties(setorDto, setor);

        var unidade = unidadeRepository.findById(setorDto.id_unidade());

        if (unidade.isPresent()) {
            setor.setUnidade(unidade.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_unidade nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(setorRepository.save(setor));
    }

    @PutMapping(value = "/{idSetor}")
    public ResponseEntity<Object> editarSetor(@PathVariable(value = "idSetor") UUID id, @RequestBody @Valid SetorDto setorDto) {
        Optional<SetorModel> setorBuscado = setorRepository.findById(id);

        if (setorBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setor nao encontrado");
        }

        SetorModel setor = setorBuscado.get();
        BeanUtils.copyProperties(setorDto, setor);

        var unidade = unidadeRepository.findById(setorDto.id_unidade());

        if (unidade.isPresent()) {
            setor.setUnidade(unidade.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_unidade nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(setorRepository.save(setor));
    }

    @DeleteMapping("/{idSetor}")
    public ResponseEntity<Object> deleterSetor(@PathVariable(value =  "idSetor") UUID id) {
        Optional<SetorModel> setorBuscado = setorRepository.findById(id);

        if (setorBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setor nao encontrado");
        }

        setorRepository.delete(setorBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Setor deletado com sucesso");
    }
}
