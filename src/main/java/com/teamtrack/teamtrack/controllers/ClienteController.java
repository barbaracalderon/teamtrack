package com.teamtrack.teamtrack.controllers;

import com.teamtrack.teamtrack.datasource.entities.ClienteEntity;
import com.teamtrack.teamtrack.dtos.RequestClienteDTO;
import com.teamtrack.teamtrack.dtos.ResponseClienteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.teamtrack.teamtrack.services.ClienteService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ResponseClienteDTO> criarCliente(@Valid @RequestBody RequestClienteDTO requestClientDTO) {
        log.info("POST /clientes ---> Chamada para o método.");

        ClienteEntity clienteEntitySalvo = clienteService.criarCliente(requestClientDTO);
        ResponseClienteDTO responseClienteDTO = clienteService.criarResponseClienteDTO(clienteEntitySalvo);

        if (responseClienteDTO != null) {
            log.info("POST /clientes ---> sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseClienteDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseClienteDTO>> listarClientes() {
        log.info("GET /clientes ---> Chamada para o método.");
        List<ClienteEntity> clienteEntityList = clienteService.listarClientes();
        if (clienteEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseClienteDTO> responseClienteDTOList = clienteService.criarResponseClienteDTO(clienteEntityList);
            log.info("GET /clientes ---> Sucesso.");
            return ResponseEntity.ok().body(responseClienteDTOList);
        }
    }


}
