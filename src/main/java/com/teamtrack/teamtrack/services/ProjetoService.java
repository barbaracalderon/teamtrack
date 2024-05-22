package com.teamtrack.teamtrack.services;

import com.teamtrack.teamtrack.datasource.entities.AtividadeEntity;
import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.datasource.entities.StatusEnum;
import com.teamtrack.teamtrack.datasource.repositories.ProjetoRepository;
import com.teamtrack.teamtrack.dtos.RequestProjetoDTO;
import com.teamtrack.teamtrack.dtos.ResponseAtividadeDTO;
import com.teamtrack.teamtrack.dtos.ResponseProjetoDTO;
import com.teamtrack.teamtrack.dtos.ResponseProjetoEmAbertoDTO;
import com.teamtrack.teamtrack.exceptions.ProjetoExistenteException;
import com.teamtrack.teamtrack.exceptions.ProjetoNotFoundException;
import com.teamtrack.teamtrack.interfaces.ProjetoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService implements ProjetoInterface {

    private final ProjetoRepository projetoRepository;
    private final ClienteService clienteService;
    private final AtividadeService atividadeService;

    @Autowired
    public ProjetoService(
            ProjetoRepository projetoRepository,
            ClienteService clienteService,
            AtividadeService facadeService
    ) {
        this.projetoRepository = projetoRepository;
        this.clienteService = clienteService;
        this.atividadeService = facadeService;
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

    public ProjetoEntity buscarProjetoPorId(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ProjetoNotFoundException("Id do Projeto não encontrado: " + id));
    }

    public List<ProjetoEntity> listarProjetosEmAberto() {
        List<ProjetoEntity> projetoEntityList = buscarProjetosPorStatusProjeto(StatusEnum.EM_ABERTO);
        if (projetoEntityList.isEmpty()) {
            throw new ProjetoNotFoundException("Nenhum projeto com status 'EM_ABERTO'.");
        } else {
            return projetoEntityList;
        }
    }

    private List<ProjetoEntity> buscarProjetosPorStatusProjeto(StatusEnum statusProjeto) {
        return projetoRepository.findByStatusProjeto(statusProjeto);
    }

    public ResponseProjetoEmAbertoDTO criarResponseProjetoEmAbertoDTO(ProjetoEntity projetoEntitySalvo) {
        ClienteEntity clienteEntity = clienteService.buscarClientePorId(projetoEntitySalvo.getIdCliente());
        List<AtividadeEntity> atividadeEntityList = atividadeService.buscarAtividadesPorIdProjeto(projetoEntitySalvo.getId());
        List<ResponseAtividadeDTO> responseAtividadeDTOList = atividadeService.criarResponseAtividadeDTO(atividadeEntityList);
        return new ResponseProjetoEmAbertoDTO(
                projetoEntitySalvo.getId(),
                projetoEntitySalvo.getNomeProjeto(),
                clienteEntity.getNomeCliente(),
                responseAtividadeDTOList
        );

    }

    public List<ResponseProjetoEmAbertoDTO> criarResponseProjetoEmAbertoDTO(List<ProjetoEntity> projetoEntityList) {
        return projetoEntityList.stream()
                .map(this::criarResponseProjetoEmAbertoDTO)
                .collect(Collectors.toList());
    }

}
