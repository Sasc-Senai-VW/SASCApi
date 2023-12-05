package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record ConsumoEquipamentoDto(
        @NotBlank BigDecimal consumo,
        @NotBlank Date data_consumo,

        @NotNull UUID id_equipamento

        ) {
}
