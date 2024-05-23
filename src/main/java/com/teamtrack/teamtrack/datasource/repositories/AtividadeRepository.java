package com.teamtrack.teamtrack.datasource.repositories;

import com.teamtrack.teamtrack.datasource.entities.AtividadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AtividadeRepository extends JpaRepository<AtividadeEntity, Long> {
    List<AtividadeEntity> findAllByIdProjeto(Long idProjeto);
}
