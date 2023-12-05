package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;

public record TipoEquipamentoDto(
        @NotBlank String tipo
) {
}
