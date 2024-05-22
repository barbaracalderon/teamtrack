package com.teamtrack.teamtrack.controllers;

import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.dtos.RequestClienteDTO;
import com.teamtrack.teamtrack.dtos.ResponseClienteDTO;
import com.teamtrack.teamtrack.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarCliente_Success() {
        RequestClienteDTO requestClienteDTO = new RequestClienteDTO("Cliente 1");
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        clienteEntity.setNomeCliente("Cliente 1");
        ResponseClienteDTO responseClienteDTO = new ResponseClienteDTO(1L, "Cliente 1");

        when(clienteService.criarCliente(any(RequestClienteDTO.class))).thenReturn(clienteEntity);
        when(clienteService.criarResponseClienteDTO(any(ClienteEntity.class))).thenReturn(responseClienteDTO);

        ResponseEntity<ResponseClienteDTO> response = clienteController.criarCliente(requestClienteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        assertEquals("Cliente 1", response.getBody().nomeCliente());
    }

    @Test
    void listarClientes_Success() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        clienteEntity.setNomeCliente("Cliente 1");
        List<ClienteEntity> clienteEntityList = Arrays.asList(clienteEntity);
        ResponseClienteDTO responseClienteDTO = new ResponseClienteDTO(1L, "Cliente 1");

        when(clienteService.listarClientes()).thenReturn(clienteEntityList);
        when(clienteService.criarResponseClienteDTO(any(List.class))).thenReturn(Arrays.asList(responseClienteDTO));

        ResponseEntity<List<ResponseClienteDTO>> response = clienteController.listarClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().get(0).id());
        assertEquals("Cliente 1", response.getBody().get(0).nomeCliente());
    }
}
