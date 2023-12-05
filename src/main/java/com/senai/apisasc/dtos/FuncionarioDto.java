package com.senai.apisasc.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FuncionarioDto(
        @NotBlank String nome,
        @NotBlank @Email(message = "O email deve estar no formato válido") String email,
        @NotBlank String senha,

        @NotNull UUID id_setor,
        @NotNull UUID id_tipofuncionario
) {
}
