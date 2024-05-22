package com.teamtrack.teamtrack.dtos;

import com.teamtrack.teamtrack.datasource.entities.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestProjetoDTO(
        @NotBlank() String nomeProjeto,
        String statusProjeto,
        @NotNull Long idCliente
    ) {
    }
