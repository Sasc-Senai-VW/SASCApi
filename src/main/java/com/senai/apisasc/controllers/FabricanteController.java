package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.FabricanteDto;
import com.senai.apisasc.models.EnderecoModel;
import com.senai.apisasc.models.FabricanteModel;
import com.senai.apisasc.repositories.FabricanteRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
@RequestMapping(value = "/fabricante", produces = {"application/json"})
public class FabricanteController {
    @Autowired
    FabricanteRepository fabricanteRepository;

    @GetMapping
    public ResponseEntity<List<FabricanteModel>> listarFabricante() {
        return ResponseEntity.status(HttpStatus.OK).body(fabricanteRepository.findAll());
    }

    @GetMapping("/{idFabricante}")
    public ResponseEntity<Object> exibirFabricante(@PathVariable(value = "idFabricante")UUID id) {
        Optional<FabricanteModel> fabricanteBuscado = fabricanteRepository.findById(id);

        if (fabricanteBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(fabricanteBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarFabricante(@RequestBody @Valid FabricanteDto fabricanteDto) {
        if (fabricanteRepository.findByCnpj(fabricanteDto.cnpj()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse CNPJ ja esta cadastrada");
        }

        FabricanteModel fabricante = new FabricanteModel();
        BeanUtils.copyProperties(fabricanteDto, fabricante);

        return ResponseEntity.status(HttpStatus.CREATED).body(fabricanteRepository.save(fabricante));
    }

    @PutMapping(value = "/{idFabricante}")
    public ResponseEntity<Object> editarFabricante(@PathVariable(value = "idFabricante") UUID id, @RequestBody @Valid FabricanteDto fabricanteDto) {
        Optional<FabricanteModel> fabricanteBuscado = fabricanteRepository.findById(id);

        if (fabricanteBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fabricante nao encontrado");
        }

        FabricanteModel fabricante = fabricanteBuscado.get();
        BeanUtils.copyProperties(fabricanteDto, fabricante);

        return ResponseEntity.status(HttpStatus.CREATED).body(fabricanteRepository.save(fabricante));
    }

    @DeleteMapping("/{idFabricante}")
    public ResponseEntity<Object> deleterFabricante(@PathVariable(value = "idFabricante") UUID id) {
        Optional<FabricanteModel> fabricanteBuscado = fabricanteRepository.findById(id);

        if (fabricanteBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fabricante nao encontrado");
        }

        fabricanteRepository.delete(fabricanteBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Fabricante deletado com sucesso");
    }

}
