package com.teamtrack.teamtrack.controllers;

import com.teamtrack.teamtrack.datasource.entities.AtividadeEntity;
import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.dtos.RequestAtividadeDTO;
import com.teamtrack.teamtrack.dtos.ResponseAtividadeDTO;
import com.teamtrack.teamtrack.services.AtividadeService;
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

class AtividadeControllerTest {

    @InjectMocks
    private AtividadeController atividadeController;

    @Mock
    private AtividadeService atividadeService;

    @Mock
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarAtividade_Success() {
        RequestAtividadeDTO requestAtividadeDTO = new RequestAtividadeDTO("Atividade 1", 1L);
        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(1L);
        AtividadeEntity atividadeEntity = new AtividadeEntity();
        atividadeEntity.setId(1L);
        ResponseAtividadeDTO responseAtividadeDTO = new ResponseAtividadeDTO(1L, "Atividade 1");

        when(projetoService.buscarProjetoPorId(1L)).thenReturn(projetoEntity);
        when(atividadeService.criarAtividade(any(RequestAtividadeDTO.class))).thenReturn(atividadeEntity);
        when(atividadeService.criarResponseAtividadeDTO(any(AtividadeEntity.class))).thenReturn(responseAtividadeDTO);

        ResponseEntity<?> response = atividadeController.criarAtividade(requestAtividadeDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        RequestAtividadeDTO responseBody = (RequestAtividadeDTO) response.getBody();
        assertEquals("Atividade 1", responseBody.descricaoAtividade());
        assertEquals(1L, responseBody.idProjeto());
    }

    @Test
    void listarAtividades_Success() {
        AtividadeEntity atividadeEntity = new AtividadeEntity();
        atividadeEntity.setId(1L);
        List<AtividadeEntity> atividadeEntityList = Arrays.asList(atividadeEntity);
        ResponseAtividadeDTO responseAtividadeDTO = new ResponseAtividadeDTO(1L, "Atividade 1");

        when(atividadeService.listarAtividades()).thenReturn(atividadeEntityList);
        when(atividadeService.criarResponseAtividadeDTO(any(List.class))).thenReturn(Arrays.asList(responseAtividadeDTO));

        ResponseEntity<List<ResponseAtividadeDTO>> response = atividadeController.listarAtividades();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().get(0).id());
        assertEquals("Atividade 1", response.getBody().get(0).descricaoAtividade());
    }
}
