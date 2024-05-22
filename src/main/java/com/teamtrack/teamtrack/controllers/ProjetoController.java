package com.teamtrack.teamtrack.controllers;

import com.teamtrack.teamtrack.datasource.entities.ProjetoEntity;
import com.teamtrack.teamtrack.dtos.RequestProjetoDTO;
import com.teamtrack.teamtrack.dtos.ResponseProjetoDTO;
import com.teamtrack.teamtrack.dtos.ResponseProjetoEmAbertoDTO;
import com.teamtrack.teamtrack.exceptions.ClienteNotFoundException;
import com.teamtrack.teamtrack.exceptions.ProjetoExistenteException;
import com.teamtrack.teamtrack.exceptions.ProjetoNotFoundException;
import com.teamtrack.teamtrack.services.ProjetoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<?> criarProjeto(@Valid @RequestBody RequestProjetoDTO requestProjetoDTO) {
        try {
            log.info("POST /projetos ---> Chamada para o método.");

            try {
                ProjetoEntity projetoEntity = projetoService.criarProjeto(requestProjetoDTO);
                ResponseProjetoDTO responseProjetoDTO = projetoService.criarResponseProjetoDTO(projetoEntity);
                if (responseProjetoDTO != null) {
                    log.info("POST /projetos ---> Sucesso.");
                    return ResponseEntity.status(HttpStatus.CREATED).body(responseProjetoDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

            } catch (IllegalArgumentException | ProjetoExistenteException e ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

        } catch (ClienteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseProjetoDTO>> listarProjetos() {
        log.info("GET /projetos ---> Chamada para o método.");
        List<ProjetoEntity> projetoEntityList = projetoService.listarProjetos();
        if (projetoEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseProjetoDTO> responseProjetoDTOList = projetoService.criarResponseProjetoDTO(projetoEntityList);
            log.info("GET /projetos ---> Sucesso.");
            return ResponseEntity.ok().body(responseProjetoDTOList);
        }
    }

    @GetMapping("/em_aberto")
    public ResponseEntity<?> listarProjetosEmAberto() {
        log.info("GET /em_aberto ---> Chamada para o método.");
        try {
            List<ProjetoEntity> projetoEntityList = projetoService.listarProjetosEmAberto();
            List<ResponseProjetoEmAbertoDTO> responseProjetoEmAbertoDTOList = projetoService.criarResponseProjetoEmAbertoDTO(projetoEntityList);
            log.info("GET /em_aberto ---> Sucesso.");
            return ResponseEntity.ok().body(responseProjetoEmAbertoDTOList);

        } catch (ProjetoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
