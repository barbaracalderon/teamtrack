package com.teamtrack.teamtrack.services;

import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.datasource.repositories.ClienteRepository;
import com.teamtrack.teamtrack.dtos.RequestClienteDTO;
import com.teamtrack.teamtrack.exceptions.ClienteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarCliente() {
        RequestClienteDTO requestClienteDTO = new RequestClienteDTO("Cliente 1");
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNomeCliente("Cliente 1");

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        ClienteEntity result = clienteService.criarCliente(requestClienteDTO);
        assertEquals("Cliente 1", result.getNomeCliente());
    }

    @Test
    void testListarClientes() {
        ClienteEntity cliente1 = new ClienteEntity();
        cliente1.setId(1L);
        cliente1.setNomeCliente("Cliente 1");

        ClienteEntity cliente2 = new ClienteEntity();
        cliente2.setId(2L);
        cliente2.setNomeCliente("Cliente 2");

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<ClienteEntity> result = clienteService.listarClientes();
        assertEquals(2, result.size());
        assertEquals("Cliente 1", result.get(0).getNomeCliente());
        assertEquals("Cliente 2", result.get(1).getNomeCliente());
    }

    @Test
    void testBuscarClientePorId() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        clienteEntity.setNomeCliente("Cliente 1");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));

        ClienteEntity result = clienteService.buscarClientePorId(1L);
        assertEquals(1L, result.getId());
        assertEquals("Cliente 1", result.getNomeCliente());
    }

    @Test
    void testBuscarClientePorId_NotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.buscarClientePorId(1L);
        });

        assertEquals("Id do Cliente não encontrado: 1", exception.getMessage());
    }
}
