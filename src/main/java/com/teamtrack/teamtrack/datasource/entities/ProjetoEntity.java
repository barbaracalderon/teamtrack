package com.teamtrack.teamtrack.datasource.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projeto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProjeto;

    @Enumerated(EnumType.STRING)
    private StatusEnum statusProjeto;

    @Column(name = "id_cliente")
    private Long idCliente;

    public ProjetoEntity(String nomeProjeto, String statusProjeto, Long idCliente) {
        if (!isValidStatusProjeto(statusProjeto)){
            throw new IllegalArgumentException(("Nome do status do projeto inválido: " + statusProjeto));
        }
        this.nomeProjeto = nomeProjeto;
        this.statusProjeto = StatusEnum.valueOf(statusProjeto);
        this.idCliente = idCliente;
    }

    private boolean isValidStatusProjeto(String statusProjeto) {
        try{
            StatusEnum.valueOf(statusProjeto);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
