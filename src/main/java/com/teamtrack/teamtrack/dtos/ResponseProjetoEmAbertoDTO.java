package com.teamtrack.teamtrack.dtos;

import java.util.List;

public record ResponseProjetoEmAbertoDTO(
        Long id,
        String nomeProjeto,
        String nomeCliente,
        List<ResponseAtividadeDTO> atividades
) {
}
