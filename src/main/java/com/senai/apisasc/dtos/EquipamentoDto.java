package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record EquipamentoDto(
        @NotNull Date data_compra,

        @NotNull UUID id_setor,
        @NotNull UUID id_modelo
) {
}
