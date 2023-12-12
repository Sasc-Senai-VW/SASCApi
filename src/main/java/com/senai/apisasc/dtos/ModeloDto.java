package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ModeloDto(


        @NotBlank String modelo,
        @NotNull BigDecimal consumo_nominal,

        @NotNull UUID id_fabricante,
        @NotNull UUID id_tipoequipamento


        ) {


}
