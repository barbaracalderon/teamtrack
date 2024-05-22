package com.teamtrack.teamtrack.services;

import com.teamtrack.teamtrack.datasource.entities.AtividadeEntity;
import com.teamtrack.teamtrack.datasource.repositories.AtividadeRepository;
import com.teamtrack.teamtrack.dtos.RequestAtividadeDTO;
import com.teamtrack.teamtrack.dtos.ResponseAtividadeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AtividadeServiceTest {

    @Mock
    private AtividadeRepository atividadeRepository;

    @InjectMocks
    private AtividadeService atividadeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarAtividade() {
        RequestAtividadeDTO requestAtividadeDTO = new RequestAtividadeDTO("Test Activity", 1L);
        AtividadeEntity atividadeEntity = new AtividadeEntity();
        atividadeEntity.setId(1L);
        atividadeEntity.setDescricaoAtividade(requestAtividadeDTO.descricaoAtividade());
        atividadeEntity.setIdProjeto(requestAtividadeDTO.idProjeto());

        when(atividadeRepository.save(any(AtividadeEntity.class))).thenReturn(atividadeEntity);

        AtividadeEntity savedAtividade = atividadeService.criarAtividade(requestAtividadeDTO);

        assertEquals(requestAtividadeDTO.descricaoAtividade(), savedAtividade.getDescricaoAtividade());
        assertEquals(requestAtividadeDTO.idProjeto(), savedAtividade.getIdProjeto());
        assertEquals(1L, savedAtividade.getId());
        verify(atividadeRepository, times(1)).save(any(AtividadeEntity.class));
    }

    @Test
    void testCriarResponseAtividadeDTO() {
        AtividadeEntity atividadeEntity = new AtividadeEntity();
        atividadeEntity.setId(1L);
        atividadeEntity.setDescricaoAtividade("Test Activity");

        ResponseAtividadeDTO responseAtividadeDTO = atividadeService.criarResponseAtividadeDTO(atividadeEntity);

        assertEquals(atividadeEntity.getId(), responseAtividadeDTO.id());
        assertEquals(atividadeEntity.getDescricaoAtividade(), responseAtividadeDTO.descricaoAtividade());
    }

    @Test
    void testListarAtividades() {
        AtividadeEntity atividadeEntity1 = new AtividadeEntity();
        atividadeEntity1.setId(1L);
        atividadeEntity1.setDescricaoAtividade("Test Activity 1");

        AtividadeEntity atividadeEntity2 = new AtividadeEntity();
        atividadeEntity2.setId(2L);
        atividadeEntity2.setDescricaoAtividade("Test Activity 2");

        when(atividadeRepository.findAll()).thenReturn(Arrays.asList(atividadeEntity1, atividadeEntity2));

        List<AtividadeEntity> atividades = atividadeService.listarAtividades();

        assertEquals(2, atividades.size());
        verify(atividadeRepository, times(1)).findAll();
    }

    @Test
    void testCriarResponseAtividadeDTOList() {
        AtividadeEntity atividadeEntity1 = new AtividadeEntity();
        atividadeEntity1.setId(1L);
        atividadeEntity1.setDescricaoAtividade("Test Activity 1");

        AtividadeEntity atividadeEntity2 = new AtividadeEntity();
        atividadeEntity2.setId(2L);
        atividadeEntity2.setDescricaoAtividade("Test Activity 2");

        List<AtividadeEntity> atividadeEntities = Arrays.asList(atividadeEntity1, atividadeEntity2);

        List<ResponseAtividadeDTO> responseAtividadeDTOS = atividadeService.criarResponseAtividadeDTO(atividadeEntities);

        assertEquals(2, responseAtividadeDTOS.size());
        assertEquals(atividadeEntity1.getId(), responseAtividadeDTOS.get(0).id());
        assertEquals(atividadeEntity1.getDescricaoAtividade(), responseAtividadeDTOS.get(0).descricaoAtividade());
        assertEquals(atividadeEntity2.getId(), responseAtividadeDTOS.get(1).id());
        assertEquals(atividadeEntity2.getDescricaoAtividade(), responseAtividadeDTOS.get(1).descricaoAtividade());
    }

    @Test
    void testBuscarAtividadesPorIdProjeto() {
        AtividadeEntity atividadeEntity1 = new AtividadeEntity();
        atividadeEntity1.setId(1L);
        atividadeEntity1.setDescricaoAtividade("Test Activity 1");
        atividadeEntity1.setIdProjeto(1L);

        AtividadeEntity atividadeEntity2 = new AtividadeEntity();
        atividadeEntity2.setId(2L);
        atividadeEntity2.setDescricaoAtividade("Test Activity 2");
        atividadeEntity2.setIdProjeto(1L);

        when(atividadeRepository.findAllByIdProjeto(anyLong())).thenReturn(Arrays.asList(atividadeEntity1, atividadeEntity2));

        List<AtividadeEntity> atividades = atividadeService.buscarAtividadesPorIdProjeto(1L);

        assertEquals(2, atividades.size());
        verify(atividadeRepository, times(1)).findAllByIdProjeto(anyLong());
    }
}
