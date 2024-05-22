package com.teamtrack.teamtrack.services;

import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.datasource.entities.StatusEnum;
import com.teamtrack.teamtrack.datasource.repositories.ProjetoRepository;
import com.teamtrack.teamtrack.dtos.RequestProjetoDTO;
import com.teamtrack.teamtrack.exceptions.ProjetoExistenteException;
import com.teamtrack.teamtrack.exceptions.ProjetoNotFoundException;
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

class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarProjeto() {
        RequestProjetoDTO requestProjetoDTO = new RequestProjetoDTO("Projeto 1", "EM_ABERTO", 1L);
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);

        when(clienteService.buscarClientePorId(1L)).thenReturn(clienteEntity);
        when(projetoRepository.findByNomeProjetoAndIdCliente("Projeto 1", 1L)).thenReturn(null);
        when(projetoRepository.save(any(ProjetoEntity.class))).thenReturn(new ProjetoEntity());

        ProjetoEntity result = projetoService.criarProjetoEntity(requestProjetoDTO);
        assertEquals("Projeto 1", result.getNomeProjeto());
        assertEquals(StatusEnum.EM_ABERTO, result.getStatusProjeto());
        assertEquals(1L, result.getIdCliente());
    }

    @Test
    void testCriarProjetoExistente() {
        RequestProjetoDTO requestProjetoDTO = new RequestProjetoDTO("Projeto 1", "EM_ABERTO", 1L);
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        ProjetoEntity projetoExistente = new ProjetoEntity();
        projetoExistente.setNomeProjeto("Projeto 1");

        when(clienteService.buscarClientePorId(1L)).thenReturn(clienteEntity);
        when(projetoRepository.findByNomeProjetoAndIdCliente("Projeto 1", 1L)).thenReturn(projetoExistente);

        ProjetoExistenteException exception = assertThrows(ProjetoExistenteException.class, () -> {
            projetoService.criarProjeto(requestProjetoDTO);
        });

        assertEquals("Esse projeto para este cliente já existe: Projeto 1", exception.getMessage());
    }

    @Test
    void testListarProjetos() {
        ProjetoEntity projeto1 = new ProjetoEntity();
        projeto1.setId(1L);
        projeto1.setNomeProjeto("Projeto 1");

        ProjetoEntity projeto2 = new ProjetoEntity();
        projeto2.setId(2L);
        projeto2.setNomeProjeto("Projeto 2");

        when(projetoRepository.findAll()).thenReturn(Arrays.asList(projeto1, projeto2));

        List<ProjetoEntity> result = projetoService.listarProjetos();
        assertEquals(2, result.size());
        assertEquals("Projeto 1", result.get(0).getNomeProjeto());
        assertEquals("Projeto 2", result.get(1).getNomeProjeto());
    }

    @Test
    void testBuscarProjetoPorId() {
        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(1L);
        projetoEntity.setNomeProjeto("Projeto 1");

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoEntity));

        ProjetoEntity result = projetoService.buscarProjetoPorId(1L);
        assertEquals(1L, result.getId());
        assertEquals("Projeto 1", result.getNomeProjeto());
    }

    @Test
    void testBuscarProjetoPorId_NotFound() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        ProjetoNotFoundException exception = assertThrows(ProjetoNotFoundException.class, () -> {
            projetoService.buscarProjetoPorId(1L);
        });

        assertEquals("Id do Projeto não encontrado: 1", exception.getMessage());
    }

    @Test
    void testListarProjetosEmAberto() {
        ProjetoEntity projeto1 = new ProjetoEntity();
        projeto1.setId(1L);
        projeto1.setNomeProjeto("Projeto 1");
        projeto1.setStatusProjeto(StatusEnum.EM_ABERTO);

        ProjetoEntity projeto2 = new ProjetoEntity();
        projeto2.setId(2L);
        projeto2.setNomeProjeto("Projeto 2");
        projeto2.setStatusProjeto(StatusEnum.EM_ABERTO);

        when(projetoRepository.findByStatusProjeto(StatusEnum.EM_ABERTO)).thenReturn(Arrays.asList(projeto1, projeto2));

        List<ProjetoEntity> result = projetoService.listarProjetosEmAberto();
        assertEquals(2, result.size());
        assertEquals("Projeto 1", result.get(0).getNomeProjeto());
        assertEquals("Projeto 2", result.get(1).getNomeProjeto());
    }

    @Test
    void testListarProjetosEmAberto_NotFound() {
        when(projetoRepository.findByStatusProjeto(StatusEnum.EM_ABERTO)).thenReturn(Arrays.asList());

        ProjetoNotFoundException exception = assertThrows(ProjetoNotFoundException.class, () -> {
            projetoService.listarProjetosEmAberto();
        });

        assertEquals("Nenhum projeto com status 'EM_ABERTO'.", exception.getMessage());
    }
}
