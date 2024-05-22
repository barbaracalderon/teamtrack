package com.teamtrack.teamtrack.datasource.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "atividade")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricaoAtividade;

    @Column(name = "id_projeto")
    private Long idProjeto;

    @ManyToOne
    @JoinColumn(name = "id_projeto", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjetoEntity projetoEntity;

    public AtividadeEntity(String descricaoAtividade, Long idProjeto) {
        this.descricaoAtividade = descricaoAtividade;
        this.idProjeto = idProjeto;
    }

}
