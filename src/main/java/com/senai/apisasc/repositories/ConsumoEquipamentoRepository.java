package com.senai.apisasc.repositories;

import com.senai.apisasc.models.ConsumoEquipamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsumoEquipamentoRepository extends JpaRepository<ConsumoEquipamentoModel, UUID> {
}