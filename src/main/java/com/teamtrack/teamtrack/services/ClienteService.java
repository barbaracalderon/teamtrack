package com.teamtrack.teamtrack.services;

import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.datasource.repositories.ClienteRepository;
import com.teamtrack.teamtrack.dtos.RequestClienteDTO;
import com.teamtrack.teamtrack.dtos.ResponseClienteDTO;
import com.teamtrack.teamtrack.interfaces.ClienteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService implements ClienteInterface {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteEntity criarCliente(RequestClienteDTO requestClienteDTO) {
        ClienteEntity clienteEntity = criarClienteEntity(requestClienteDTO);
        return clienteRepository.save(clienteEntity);
    }

    @Override
    public ClienteEntity criarClienteEntity(RequestClienteDTO requestClienteDTO){
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNomeCliente(requestClienteDTO.nomeCliente());
        return clienteEntity;
    }

    @Override
    public List<ClienteEntity> listarClientes() {
        return clienteRepository.findAll();
    }

    public ResponseClienteDTO criarResponseClienteDTO(ClienteEntity clienteEntitySalvo){
        return new ResponseClienteDTO(
                clienteEntitySalvo.getId(),
                clienteEntitySalvo.getNomeCliente()
        );
    }

    public List<ResponseClienteDTO> criarResponseClienteDTO(List<ClienteEntity> clienteEntityList){
        return clienteEntityList.stream()
                .map(this::criarResponseClienteDTO)
                .collect(Collectors.toList());
    }
}
