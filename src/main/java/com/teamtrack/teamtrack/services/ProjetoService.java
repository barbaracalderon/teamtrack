package com.teamtrack.teamtrack.services;

import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.datasource.entities.StatusEnum;
import com.teamtrack.teamtrack.datasource.repositories.ProjetoRepository;
import com.teamtrack.teamtrack.dtos.RequestProjetoDTO;
import com.teamtrack.teamtrack.dtos.ResponseProjetoDTO;
import com.teamtrack.teamtrack.exceptions.ProjetoExistenteException;
import com.teamtrack.teamtrack.interfaces.ProjetoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService implements ProjetoInterface {

    private final ProjetoRepository projetoRepository;
    private final ClienteService clienteService;

    @Autowired
    public ProjetoService(
            ProjetoRepository projetoRepository,
            ClienteService clienteService
    ) {
        this.projetoRepository = projetoRepository;
        this.clienteService = clienteService;
    }

    @Override
    public ProjetoEntity criarProjeto(RequestProjetoDTO requestProjetoDTO) {
        ClienteEntity clienteEntity = clienteService.buscarClientePorId(requestProjetoDTO.idCliente());
        ProjetoEntity projetoExistente = buscarProjetoPorNomeProjetoAndIdCliente(requestProjetoDTO.nomeProjeto(), requestProjetoDTO.idCliente());

        if (projetoExistente == null) {
            ProjetoEntity projetoEntity = criarProjetoEntity(requestProjetoDTO);
            return projetoRepository.save(projetoEntity);
        } else throw new ProjetoExistenteException("Esse projeto para este cliente já existe: " + projetoExistente.getNomeProjeto());
    }


    private ProjetoEntity buscarProjetoPorNomeProjetoAndIdCliente(String nomeProjeto, Long idCliente) {
        return projetoRepository.findByNomeProjetoAndIdCliente(nomeProjeto, idCliente);
    }

    @Override
    public ProjetoEntity criarProjetoEntity(RequestProjetoDTO requestProjetoDTO) {
        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setNomeProjeto(requestProjetoDTO.nomeProjeto());
        projetoEntity.setStatusProjeto(StatusEnum.valueOf(requestProjetoDTO.statusProjeto()));
        projetoEntity.setIdCliente(requestProjetoDTO.idCliente());
        return projetoEntity;
    }

    @Override
    public List<ProjetoEntity> listarProjetos() {
        return projetoRepository.findAll();
    }

    public ResponseProjetoDTO criarResponseProjetoDTO(ProjetoEntity projetoEntitySalvo) {
        return new ResponseProjetoDTO(
                projetoEntitySalvo.getId(),
                projetoEntitySalvo.getNomeProjeto(),
                projetoEntitySalvo.getStatusProjeto(),
                projetoEntitySalvo.getIdCliente()
        );
    }

    public List<ResponseProjetoDTO> criarResponseProjetoDTO(List<ProjetoEntity> projetoEntityList){
        return projetoEntityList.stream()
                .map(this::criarResponseProjetoDTO)
                .collect(Collectors.toList());
    }


}
