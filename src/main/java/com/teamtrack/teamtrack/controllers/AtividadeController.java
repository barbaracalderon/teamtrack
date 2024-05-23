package com.teamtrack.teamtrack.controllers;

import com.teamtrack.teamtrack.datasource.entities.AtividadeEntity;
import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.dtos.RequestAtividadeDTO;
import com.teamtrack.teamtrack.dtos.ResponseAtividadeDTO;
import com.teamtrack.teamtrack.exceptions.ProjetoNotFoundException;

import com.teamtrack.teamtrack.services.AtividadeService;
import com.teamtrack.teamtrack.services.ProjetoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;
    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<?> criarAtividade(@Valid @RequestBody RequestAtividadeDTO requestAtividadeDTO) {

        try {
            log.info("POST /atividades ---> Chamada para o método.");
            ProjetoEntity projetoEntity = projetoService.buscarProjetoPorId(requestAtividadeDTO.idProjeto());
            AtividadeEntity atividadeEntitySalvo = atividadeService.criarAtividade(requestAtividadeDTO);
            ResponseAtividadeDTO responseAtividadeDTO = atividadeService.criarResponseAtividadeDTO(atividadeEntitySalvo);

            if (responseAtividadeDTO != null) {
                log.info("POST /atividades ---> Sucesso.");
                return ResponseEntity.status(HttpStatus.CREATED).body(requestAtividadeDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        } catch (ProjetoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping()
    public ResponseEntity<List<ResponseAtividadeDTO>> listarAtividades() {
        log.info("GET /atividades ---> Chamada para o método.");
        List<AtividadeEntity> atividadeEntityList = atividadeService.listarAtividades();

        if (atividadeEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseAtividadeDTO> responseAtividadeDTOList = atividadeService.criarResponseAtividadeDTO(atividadeEntityList);
            log.info("GET /atividades ---> Sucesso.");
            return ResponseEntity.ok().body(responseAtividadeDTOList);
        }

    }

}