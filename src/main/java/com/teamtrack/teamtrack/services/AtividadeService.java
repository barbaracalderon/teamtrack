package com.teamtrack.teamtrack.services;

import com.teamtrack.teamtrack.datasource.entities.AtividadeEntity;
import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.datasource.repositories.AtividadeRepository;
import com.teamtrack.teamtrack.dtos.RequestAtividadeDTO;
import com.teamtrack.teamtrack.dtos.ResponseAtividadeDTO;
import com.teamtrack.teamtrack.interfaces.AtividadeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService implements AtividadeInterface {

    private final AtividadeRepository atividadeRepository;

    @Autowired
    public AtividadeService(
            AtividadeRepository atividadeRepository
    ) {
        this.atividadeRepository = atividadeRepository;
    }

    @Override
    public AtividadeEntity criarAtividade(RequestAtividadeDTO requestAtividadeDTO) {
        AtividadeEntity atividadeEntity = criarAtividadeEntity(requestAtividadeDTO);
        return atividadeRepository.save(atividadeEntity);
    }

    private AtividadeEntity criarAtividadeEntity(RequestAtividadeDTO requestAtividadeDTO){
        AtividadeEntity atividadeEntity = new AtividadeEntity();
        atividadeEntity.setDescricaoAtividade(requestAtividadeDTO.descricaoAtividade());
        atividadeEntity.setIdProjeto(requestAtividadeDTO.idProjeto());
        return atividadeEntity;
    }

    public ResponseAtividadeDTO criarResponseAtividadeDTO(AtividadeEntity atividadeEntitySalvo) {
        return new ResponseAtividadeDTO(
                atividadeEntitySalvo.getId(),
                atividadeEntitySalvo.getDescricaoAtividade()
        );
    }

    @Override
    public List<AtividadeEntity> listarAtividades() {
        return atividadeRepository.findAll();
    }

    public List<ResponseAtividadeDTO> criarResponseAtividadeDTO(List<AtividadeEntity> atividadeEntityList) {
        return atividadeEntityList.stream()
                .map(this::criarResponseAtividadeDTO)
                .collect(Collectors.toList());
    }

    public List<AtividadeEntity> buscarAtividadesPorIdProjeto(Long idProjeto){
        return atividadeRepository.findAllByIdProjeto(idProjeto);
    }

}
