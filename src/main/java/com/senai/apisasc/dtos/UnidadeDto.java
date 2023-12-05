package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnidadeDto(
        @NotBlank String nome,
        @NotNull UUID id_endereco
) {
}
