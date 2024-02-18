package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.EquipamentoDto;
import com.senai.apisasc.models.EquipamentoModel;
import com.senai.apisasc.repositories.EquipamentoRepository;
import com.senai.apisasc.repositories.ModeloRepository;
import com.senai.apisasc.repositories.SetorRepository;
import com.senai.apisasc.services.EquipamentoService;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping(value = "/equipamento", produces = {"application/json"})
public class EquipamentoController {
    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    SetorRepository setorRepository;

    @Autowired
    ModeloRepository modeloRepository;

    @Autowired
    EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public ResponseEntity<List<EquipamentoModel>> listarEquipamentos() {
        return ResponseEntity.status(HttpStatus.OK).body(equipamentoRepository.findAll());
    }

    @GetMapping("/{idEquipamento}")
    public ResponseEntity<Object> exibirEquipamentos(@PathVariable(value = "idEquipamento")UUID id) {
        Optional<EquipamentoModel> equipamentoBuscado = equipamentoRepository.findById(id);

        if (equipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(equipamentoBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarEquipamento(@RequestBody @Valid EquipamentoDto equipamentoDto) {
        EquipamentoModel equipamento = new EquipamentoModel();
        BeanUtils.copyProperties(equipamentoDto, equipamento);

        var setor = setorRepository.findById(equipamentoDto.id_setor());
        var modelo = modeloRepository.findById(equipamentoDto.id_modelo());

        if (setor.isPresent()) {
            equipamento.setSetor(setor.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_setor nao encontrado");
        }

        if (modelo.isPresent()) {
            equipamento.setModelo(modelo.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_modelo nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoRepository.save(equipamento));
    }

    @PutMapping(value = "/{idEquipamento}")
    public ResponseEntity<Object> editarEquipamento(@PathVariable(value = "idEquipamento") UUID id, @RequestBody @Valid EquipamentoDto equipamentoDto) {
        Optional<EquipamentoModel> equipamentoBuscado = equipamentoRepository.findById(id);

        if (equipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipamento nao encontrado");
        }

        EquipamentoModel equipamento = equipamentoBuscado.get();
        BeanUtils.copyProperties(equipamentoDto, equipamento);

        var setor = setorRepository.findById(equipamentoDto.id_setor());
        var modelo = modeloRepository.findById(equipamentoDto.id_modelo());

        if (setor.isPresent()) {
            equipamento.setSetor(setor.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_setor nao encontrado");
        }

        if (modelo.isPresent()) {
            equipamento.setModelo(modelo.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_modelo nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoRepository.save(equipamento));
    }

    @DeleteMapping("/{idEquipamento}")
    public ResponseEntity<Object> deleterEquipamento(@PathVariable(value = "idEquipamento") UUID id) {
        Optional<EquipamentoModel> equipamentoBuscado = equipamentoRepository.findById(id);



        try {
            equipamentoService.deleteEquipamentoAndRelatedData(equipamentoBuscado.get().getId());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Equipamento deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar equipamento");
        }



    }
//    public ResponseEntity<Object> deleteEquipamento(@PathVariable(value = "idEquipamento") UUID id) {
//        try {
//            equipamentoService.deleteEquipamentoAndRelatedData(id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Equipamento deletado com sucesso");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipamento não encontrado");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar equipamento");
//        }
//    }


}
