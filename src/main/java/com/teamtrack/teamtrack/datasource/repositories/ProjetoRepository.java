package com.teamtrack.teamtrack.datasource.repositories;

import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Long> {

    ProjetoEntity findByNomeProjetoAndIdCliente(String nomeProjeto, Long idCliente);
}
