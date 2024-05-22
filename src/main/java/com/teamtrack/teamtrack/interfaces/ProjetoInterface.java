package com.teamtrack.teamtrack.interfaces;

import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.dtos.RequestProjetoDTO;

import java.util.List;

public interface ProjetoInterface {
    ProjetoEntity criarProjeto(RequestProjetoDTO requestProjetoDTO);

    ProjetoEntity criarProjetoEntity(RequestProjetoDTO requestProjetoDTO);

    List<ProjetoEntity> listarProjetos();

}
