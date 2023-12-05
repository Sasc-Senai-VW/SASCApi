package com.senai.apisasc.repositories;

import com.senai.apisasc.models.EquipamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipamentoRepository extends JpaRepository<EquipamentoModel, UUID> {
}