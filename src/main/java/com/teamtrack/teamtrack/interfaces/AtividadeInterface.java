package com.teamtrack.teamtrack.interfaces;

import com.teamtrack.teamtrack.datasource.entities.AtividadeEntity;
import com.teamtrack.teamtrack.dtos.RequestAtividadeDTO;

import java.util.List;

public interface AtividadeInterface {
    AtividadeEntity criarAtividade(RequestAtividadeDTO requestAtividadeDTO);

    List<AtividadeEntity> listarAtividades();

    List<AtividadeEntity> buscarAtividadesPorIdProjeto(Long idProjeto);
}
