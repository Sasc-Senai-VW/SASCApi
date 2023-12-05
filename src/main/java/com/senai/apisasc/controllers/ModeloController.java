package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.ModeloDto;
import com.senai.apisasc.models.ModeloModel;
import com.senai.apisasc.repositories.FabricanteRepository;
import com.senai.apisasc.repositories.ModeloRepository;
import com.senai.apisasc.repositories.TipoEquipamentoRepository;
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
@RequestMapping(value = "/modelo", produces = {"application/json"})
public class ModeloController {
    @Autowired
    ModeloRepository modeloRepository;

    @Autowired
    FabricanteRepository fabricanteRepository;

    @Autowired
    TipoEquipamentoRepository tipoEquipamentoRepository;

    @GetMapping
    public ResponseEntity<List<ModeloModel>> listarModelo() {
        return ResponseEntity.status(HttpStatus.OK).body(modeloRepository.findAll());
    }

    @GetMapping("/{idModelo}")
    public ResponseEntity<Object> exibirModelo(@PathVariable(value = "idModelo")UUID id) {
        Optional<ModeloModel> modeloBuscado = modeloRepository.findById(id);

        if (modeloBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modelo nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(modeloBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarModelo(@RequestBody @Valid ModeloDto modeloDto) {
        if (modeloRepository.findByModelo(modeloDto.modelo()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse modelo ja esta cadastrado");
        }

        ModeloModel modelo = new ModeloModel();
        BeanUtils.copyProperties(modeloDto, modelo);

        var fabricante = fabricanteRepository.findById(modeloDto.id_fabricante());

        var tipoequipamento = tipoEquipamentoRepository.findById(modeloDto.id_tipoequipamento());

        if (fabricante.isPresent()) {
            modelo.setFabricante(fabricante.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_fabricante nao encontrado");
        }

        if (tipoequipamento.isPresent()) {
            modelo.setTipoequipamento(tipoequipamento.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_tipoequipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(modeloRepository.save(modelo));

    }

    @PutMapping(value = "{idModelo}")
    public ResponseEntity<Object> editarModelo(@PathVariable(value = "idModelo") UUID id, @RequestBody @Valid ModeloDto modeloDto) {
        Optional<ModeloModel> modeloBuscado = modeloRepository.findById(id);

        if (modeloBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modelo nao encontrado");
        }

        ModeloModel modelo = modeloBuscado.get();
        BeanUtils.copyProperties(modeloDto, modelo);

        var fabricante = fabricanteRepository.findById(modeloDto.id_fabricante());

        var tipoequipamento = tipoEquipamentoRepository.findById(modeloDto.id_tipoequipamento());

        if (fabricante.isPresent()) {
            modelo.setFabricante(fabricante.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_fabricante nao encontrado");
        }

        if (tipoequipamento.isPresent()) {
            modelo.setTipoequipamento(tipoequipamento.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_tipoequipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(modeloRepository.save(modelo));
    }

    @DeleteMapping("/{idModelo}")
    public ResponseEntity<Object> deletarModelo(@PathVariable(value = "idModelo") UUID id) {
        Optional<ModeloModel> modeloBuscado = modeloRepository.findById(id);

        if (modeloBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modelo nao encotrado");
        }

        modeloRepository.delete(modeloBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Modelo deletado com sucesso");
    }

}
