package com.teamtrack.teamtrack.dtos;

import com.teamtrack.teamtrack.datasource.entities.StatusEnum;

public record ResponseProjetoDTO(
        Long id,
        String nomeProjeto,
        StatusEnum statusProjeto,
        Long idCliente
) {
}
