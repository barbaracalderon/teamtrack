package com.teamtrack.teamtrack.dtos;

import jakarta.validation.constraints.NotBlank;

public record RequestClienteDTO(
        @NotBlank() String nomeCliente
    ) { }
