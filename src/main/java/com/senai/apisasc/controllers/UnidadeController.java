package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.UnidadeDto;
import com.senai.apisasc.models.UnidadeModel;
import com.senai.apisasc.repositories.EnderecoRepository;
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
@RequestMapping(value = "/unidade", produces = {"application/json"})
public class UnidadeController {
    @Autowired
    UnidadeRepository unidadeRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<UnidadeModel>> listarUnidades() {
        return ResponseEntity.status(HttpStatus.OK).body(unidadeRepository.findAll());
    }

    @GetMapping("/{idUnidade}")
    public ResponseEntity<Object> exibirUnidade(@PathVariable(value = "idUnidade")UUID id) {
        Optional<UnidadeModel> unidadeBuscada = unidadeRepository.findById(id);

        if (unidadeBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade nao encontrada");
        }

        return ResponseEntity.status(HttpStatus.OK).body(unidadeBuscada.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarUnidade(@RequestBody @Valid UnidadeDto unidadeDto){
        UnidadeModel unidade = new UnidadeModel();
        BeanUtils.copyProperties(unidadeDto, unidade);

        var endereco = enderecoRepository.findById(unidadeDto.id_endereco());

        if (endereco.isPresent()) {
            unidade.setEndereco(endereco.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_endereco nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeRepository.save(unidade));
    }

    @PutMapping(value = "/{idUnidade}")
    public ResponseEntity<Object> editarUnidade(@PathVariable(value = "idUnidade") UUID id, @RequestBody @Valid UnidadeDto unidadeDto) {
        Optional<UnidadeModel> unidadeBuscada = unidadeRepository.findById(id);

        if (unidadeBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade nao encontrada");
        }

        UnidadeModel unidade = unidadeBuscada.get();
        BeanUtils.copyProperties(unidadeDto, unidade);

        var endereco = enderecoRepository.findById(unidadeDto.id_endereco());

        if (endereco.isPresent()) {
            unidade.setEndereco(endereco.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_endereco nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeRepository.save(unidade));
    }

    @DeleteMapping("/{idUnidade}")
    public ResponseEntity<Object> deleterUnidade(@PathVariable(value = "idUnidade") UUID id) {
        Optional<UnidadeModel> unidadeBuscada = unidadeRepository.findById(id);

        if (unidadeBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade nao encontrada");
        }

        unidadeRepository.delete(unidadeBuscada.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Unidade deletado com sucesso");
    }
}
