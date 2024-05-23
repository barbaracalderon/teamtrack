package com.teamtrack.teamtrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestAtividadeDTO (
        @NotBlank() String descricaoAtividade,
        @NotNull() Long idProjeto
    ){
    }
