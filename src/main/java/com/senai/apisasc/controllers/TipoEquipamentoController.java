package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.TipoEquipamentoDto;
import com.senai.apisasc.models.TipoEquipamentoModel;
import com.senai.apisasc.models.TipoFuncionarioModel;
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
@RequestMapping (value = "/tipoequipamento", produces = {"application/json"})
public class TipoEquipamentoController {
    @Autowired
    TipoEquipamentoRepository tipoEquipamentoRepository;

    @GetMapping
    public ResponseEntity<List<TipoEquipamentoModel>> listarTipoEquipamento() {
        return ResponseEntity.status(HttpStatus.OK).body(tipoEquipamentoRepository.findAll());
    }

    @GetMapping("/{idTipoEquipamento}")
    public ResponseEntity<Object> exibirTipoEquipamento(@PathVariable(value = "idTipoEquipamento")UUID id) {
        Optional<TipoEquipamentoModel> tipoequipamentoBuscado = tipoEquipamentoRepository.findById(id);

        if (tipoequipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Equipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(tipoequipamentoBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarTipoEquipamento (@RequestBody @Valid TipoEquipamentoDto tipoEquipamentoDto) {
      if (tipoEquipamentoRepository.findByTipo(tipoEquipamentoDto.tipo()) != null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse tipo equipamento ja esta cadastrado");
      }

        TipoEquipamentoModel tipoequipamento = new TipoEquipamentoModel();
      BeanUtils.copyProperties(tipoEquipamentoDto, tipoequipamento);

      return ResponseEntity.status(HttpStatus.CREATED).body(tipoEquipamentoRepository.save(tipoequipamento));
    }

    @PutMapping(value = "/{idTipoEquipamento}")
    public ResponseEntity<Object> editarTipoEquipamento(@PathVariable(value = "idTipoEquipamento") UUID id, @RequestBody @Valid TipoEquipamentoDto tipoEquipamentoDto) {
        Optional<TipoEquipamentoModel> tipoequipamentoBuscado = tipoEquipamentoRepository.findById(id);

        if (tipoequipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Equipamento nao encontrado");
        }

        TipoEquipamentoModel tipoequipamento = tipoequipamentoBuscado.get();
        BeanUtils.copyProperties(tipoEquipamentoDto, tipoequipamento);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoEquipamentoRepository.save(tipoequipamento));
    }

    @DeleteMapping("/{idTipoEquipamento}")
    public ResponseEntity<Object> deletarTipoEquipamento(@PathVariable(value = "idTipoEquipamento") UUID id) {
        Optional<TipoEquipamentoModel> tipoquipamentoBuscado = tipoEquipamentoRepository.findById(id);

        if (tipoquipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Equipamento nao encontrado");
        }

        tipoEquipamentoRepository.delete(tipoquipamentoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tipo Equipamento deletado com sucesso");
    }
}
