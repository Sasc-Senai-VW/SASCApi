package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.FuncionarioDto;
import com.senai.apisasc.models.FuncionarioModel;
import com.senai.apisasc.repositories.FuncionarioRepository;
import com.senai.apisasc.repositories.SetorRepository;
import com.senai.apisasc.repositories.TipoFuncionarioRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/funcionario", produces = {"application/json"})
public class FuncionarioController {
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    private SetorRepository setorRepository;
    @Autowired
    private TipoFuncionarioRepository tipoFuncionarioRepository;

    @GetMapping
    public ResponseEntity<List<FuncionarioModel>> listarFuncionarios() {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.findAll());
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Object> exibirFuncionario(@PathVariable(value = "idFuncionario") UUID id) {
        Optional<FuncionarioModel> funcionarioBuscado = funcionarioRepository.findById(id);

        if (funcionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(funcionarioBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarFuncionario(@RequestBody @Valid FuncionarioDto funcionarioDto) {
        if (funcionarioRepository.findByEmail(funcionarioDto.email()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse email já está cadastrado!");
        }

        FuncionarioModel funcionario = new FuncionarioModel();
        BeanUtils.copyProperties(funcionarioDto, funcionario);

        var setor = setorRepository.findById(funcionarioDto.id_setor());
        var tipofuncionario = tipoFuncionarioRepository.findById(funcionarioDto.id_tipofuncionario());



        if (setor.isPresent()) {
            funcionario.setSetor(setor.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_setor nao encontrado");
        }

        if (tipofuncionario.isPresent()) {
            funcionario.setTipofuncionario(tipofuncionario.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_tipofuncionario nao encontrado");
        }

        String senhaCript = new BCryptPasswordEncoder().encode(funcionarioDto.senha());
        funcionario.setSenha(senhaCript);

        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionario));
    }

    @PutMapping(value = "/{idFuncionario}")
    public ResponseEntity<Object> editarFuncionario(@PathVariable(value = "idFuncionario") UUID id, @RequestBody @Valid FuncionarioDto funcionarioDto) {
        Optional<FuncionarioModel> funcionarioBuscado = funcionarioRepository.findById(id);

        if (funcionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario nao encontrado");
        }

        FuncionarioModel funcionario = funcionarioBuscado.get();
        BeanUtils.copyProperties(funcionarioDto, funcionario);

        var setor = setorRepository.findById(funcionarioDto.id_setor());
        var tipofuncionario = tipoFuncionarioRepository.findById(funcionarioDto.id_tipofuncionario());

        if (setor.isPresent()) {
            funcionario.setSetor(setor.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_setor nao encontrado");
        }

        if (tipofuncionario.isPresent()) {
            funcionario.setTipofuncionario(tipofuncionario.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_tipofuncionario nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionario));
    }

    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity<Object> deletarFuncionario(@PathVariable(value = "idFuncionario") UUID id) {
        Optional<FuncionarioModel> funcionarioBuscado = funcionarioRepository.findById(id);

        if (funcionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario nao encontrado");
        }

        funcionarioRepository.delete(funcionarioBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Funcionario deletado com sucesso");
    }

}


