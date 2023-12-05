package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.ConsumoEquipamentoDto;
import com.senai.apisasc.models.ConsumoEquipamentoModel;
import com.senai.apisasc.repositories.ConsumoEquipamentoRepository;
import com.senai.apisasc.repositories.EquipamentoRepository;
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
@RequestMapping(value = "/consumoequipamento" , produces = {"application/json"})
public class ConsumoEquipamentoController {
    @Autowired
    ConsumoEquipamentoRepository consumoEquipamentoRepository;

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @GetMapping
    public ResponseEntity<List<ConsumoEquipamentoModel>> listarConsumoEquiopamento() {
        return ResponseEntity.status(HttpStatus.OK).body(consumoEquipamentoRepository.findAll());
    }

    @GetMapping("/{idConsumoEquipamento}")
    public ResponseEntity<Object> exibirConsumoEquipament(@PathVariable(value = "idConsumoEquipamento")UUID id) {
        Optional<ConsumoEquipamentoModel> consumoequipamentoBuscado = consumoEquipamentoRepository.findById(id);

        if (consumoequipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo Equipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(consumoequipamentoBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarConsumoEquipamento(@RequestBody @Valid ConsumoEquipamentoDto consumoEquipamentoDto) {
        ConsumoEquipamentoModel consumoequipamento = new ConsumoEquipamentoModel();
        BeanUtils.copyProperties(consumoEquipamentoDto, consumoequipamento);

        var equipamento = equipamentoRepository.findById(consumoEquipamentoDto.id_equipamento());

        if (equipamento.isPresent()) {
            consumoequipamento.setEquipamento(equipamento.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_equipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(consumoEquipamentoRepository.save(consumoequipamento));
    }

    @PutMapping(value = "/{idConsumoEquipamento}")
    public ResponseEntity<Object> editarConsumoEquipamento(@PathVariable(value = "idConsumoEquipamento") UUID id, @RequestBody @Valid ConsumoEquipamentoDto consumoEquipamentoDto) {
        Optional<ConsumoEquipamentoModel> consumoequipamentoBuscado = consumoEquipamentoRepository.findById(id);

        if (consumoequipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo Equipamento nao encontrado");
        }

        ConsumoEquipamentoModel consumoequipamento = consumoequipamentoBuscado.get();
        BeanUtils.copyProperties(consumoEquipamentoDto, consumoequipamento);

        var equipamento = equipamentoRepository.findById(consumoEquipamentoDto.id_equipamento());

        if (equipamento.isPresent()) {
            consumoequipamento.setEquipamento(equipamento.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_equipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(consumoEquipamentoRepository.save(consumoequipamento));
    }

    @DeleteMapping("/{idConsumoEquipamento}")
    public ResponseEntity<Object> deletarConsumoEquipamento(@PathVariable(value = "idConsumoEquipamento") UUID id) {
        Optional<ConsumoEquipamentoModel> consumoequipamentoBuscado = consumoEquipamentoRepository.findById(id);

        if (consumoequipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo Equipamento nao encontrado");
        }

        consumoEquipamentoRepository.delete(consumoequipamentoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Consumo Equipamento deletado com sucesso");
    }
}
