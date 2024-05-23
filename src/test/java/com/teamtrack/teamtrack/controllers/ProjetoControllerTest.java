package com.teamtrack.teamtrack.controllers;

import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.datasource.entities.StatusEnum;
import com.teamtrack.teamtrack.dtos.RequestProjetoDTO;
import com.teamtrack.teamtrack.dtos.ResponseProjetoDTO;
import com.teamtrack.teamtrack.dtos.ResponseProjetoEmAbertoDTO;
import com.teamtrack.teamtrack.services.ProjetoService;
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

class ProjetoControllerTest {

    @InjectMocks
    private ProjetoController projetoController;

    @Mock
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarProjeto_Success() {
        RequestProjetoDTO requestProjetoDTO = new RequestProjetoDTO("Projeto 1", "EM_ABERTO", 1L);
        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(1L);
        projetoEntity.setNomeProjeto("Projeto 1");
        ResponseProjetoDTO responseProjetoDTO = new ResponseProjetoDTO(1L, "Projeto 1", StatusEnum.EM_ABERTO, 1L);

        when(projetoService.criarProjeto(any(RequestProjetoDTO.class))).thenReturn(projetoEntity);
        when(projetoService.criarResponseProjetoDTO(any(ProjetoEntity.class))).thenReturn(responseProjetoDTO);

        ResponseEntity<?> response = projetoController.criarProjeto(requestProjetoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        ResponseProjetoDTO responseBody = (ResponseProjetoDTO) response.getBody();
        assertEquals(1L, responseBody.id());
        assertEquals("Projeto 1", responseBody.nomeProjeto());
    }

    @Test
    void listarProjetos_Success() {
        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(1L);
        projetoEntity.setNomeProjeto("Projeto 1");
        List<ProjetoEntity> projetoEntityList = Arrays.asList(projetoEntity);
        ResponseProjetoDTO responseProjetoDTO = new ResponseProjetoDTO(1L, "Projeto 1", StatusEnum.EM_ABERTO, 1L);

        when(projetoService.listarProjetos()).thenReturn(projetoEntityList);
        when(projetoService.criarResponseProjetoDTO(any(List.class))).thenReturn(Arrays.asList(responseProjetoDTO));

        ResponseEntity<List<ResponseProjetoDTO>> response = projetoController.listarProjetos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().get(0).id());
        assertEquals("Projeto 1", response.getBody().get(0).nomeProjeto());
    }

    @Test
    void listarProjetosEmAberto_Success() {
        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(1L);
        projetoEntity.setNomeProjeto("Projeto 1");
        List<ProjetoEntity> projetoEntityList = Arrays.asList(projetoEntity);
        ResponseProjetoEmAbertoDTO responseProjetoEmAbertoDTO = new ResponseProjetoEmAbertoDTO(1L, "Projeto 1", "Cliente 1", null);

        when(projetoService.listarProjetosEmAberto()).thenReturn(projetoEntityList);
        when(projetoService.criarResponseProjetoEmAbertoDTO(any(List.class))).thenReturn(Arrays.asList(responseProjetoEmAbertoDTO));

        ResponseEntity<?> response = projetoController.listarProjetosEmAberto();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<ResponseProjetoEmAbertoDTO> responseBody = (List<ResponseProjetoEmAbertoDTO>) response.getBody();
        assertEquals(1L, responseBody.get(0).id());
        assertEquals("Projeto 1", responseBody.get(0).nomeProjeto());
    }
}
