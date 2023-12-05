package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SetorDto(
        @NotBlank String titulo,
        @NotNull UUID id_unidade
) {
}
