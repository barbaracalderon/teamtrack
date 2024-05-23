package com.teamtrack.teamtrack.interfaces;

import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.dtos.RequestClienteDTO;

import java.util.List;

public interface ClienteInterface {
    ClienteEntity criarCliente(RequestClienteDTO requestClienteDTO);

    ClienteEntity criarClienteEntity(RequestClienteDTO requestClienteDTO);

    List<ClienteEntity> listarClientes();
}
