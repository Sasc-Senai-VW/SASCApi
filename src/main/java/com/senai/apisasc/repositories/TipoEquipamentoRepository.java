package com.senai.apisasc.repositories;

import com.senai.apisasc.models.TipoEquipamentoModel;
import com.senai.apisasc.models.TipoFuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamentoModel, UUID> {
    TipoEquipamentoModel findByTipo(String tipo);
}