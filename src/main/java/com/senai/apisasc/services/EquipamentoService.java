package com.senai.apisasc.services;

import com.senai.apisasc.models.EquipamentoModel;
import com.senai.apisasc.repositories.EquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @Transactional
    public void deleteEquipamentoAndRelatedData(UUID id) {
        EquipamentoModel equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));

        equipamento.getConsumoEquipamentos().clear();

        equipamentoRepository.delete(equipamento);
    }
}
