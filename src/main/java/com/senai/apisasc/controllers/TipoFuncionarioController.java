package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.TipoFuncionarioDto;
import com.senai.apisasc.models.TipoFuncionarioModel;
import com.senai.apisasc.repositories.TipoFuncionarioRepository;
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
@RequestMapping(value = "/tipofuncionario", produces = {"application/json"})
public class TipoFuncionarioController {
    @Autowired
    TipoFuncionarioRepository tipoFuncionarioRepository;

    @GetMapping
    public ResponseEntity<List<TipoFuncionarioModel>> listarTipoFuncionario() {
        return ResponseEntity.status(HttpStatus.OK).body(tipoFuncionarioRepository.findAll());
    }

    @GetMapping("/{idTipoFuncionario}")
    public ResponseEntity<Object> exibirTipoFuncionario(@PathVariable(value = "idTipoFuncionario")UUID id) {
        Optional<TipoFuncionarioModel> tipofuncionarioBuscado = tipoFuncionarioRepository.findById(id);

        if (tipofuncionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Funcionario nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(tipofuncionarioBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarTipoFuncionario(@RequestBody @Valid TipoFuncionarioDto tipoFuncionarioDto) {
        if (tipoFuncionarioRepository.findByCategoria(tipoFuncionarioDto.categoria()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Essa categoria ja esta cadastrada");
        }

        TipoFuncionarioModel tipofuncionario = new TipoFuncionarioModel();
        BeanUtils.copyProperties(tipoFuncionarioDto, tipofuncionario);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoFuncionarioRepository.save(tipofuncionario));
    }

    @PutMapping(value = "/{idTipoFuncionario}")
    public ResponseEntity<Object> editarTipoFuncionario(@PathVariable(value = "idTipoFuncionario") UUID id, @RequestBody @Valid TipoFuncionarioDto tipoFuncionarioDto) {
        Optional<TipoFuncionarioModel> tipofuncionarioBuscado = tipoFuncionarioRepository.findById(id);

        if (tipofuncionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Funcionario nao encontrado");
        }

        TipoFuncionarioModel tipofuncionario = tipofuncionarioBuscado.get();
        BeanUtils.copyProperties(tipoFuncionarioDto, tipofuncionario);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoFuncionarioRepository.save(tipofuncionario));
    }

    @DeleteMapping("/{idTipoFuncionario}")
    public ResponseEntity<Object> deletarTipoFuncionario(@PathVariable(value = "idTipoFuncionario") UUID id) {
        Optional<TipoFuncionarioModel> tipofuncionarioBuscado = tipoFuncionarioRepository.findById(id);

        if (tipofuncionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Funcionario nao encontrado");
        }

        tipoFuncionarioRepository.delete(tipofuncionarioBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tipo Funcionario deletado com sucesso");
    }
}
