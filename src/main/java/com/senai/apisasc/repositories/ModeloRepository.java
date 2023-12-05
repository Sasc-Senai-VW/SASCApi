package com.senai.apisasc.repositories;

import com.senai.apisasc.models.FuncionarioModel;
import com.senai.apisasc.models.ModeloModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModeloRepository extends JpaRepository<ModeloModel, UUID> {
    ModeloModel findByModelo(String modelo);
}