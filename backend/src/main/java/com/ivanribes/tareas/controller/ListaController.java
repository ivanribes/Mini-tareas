package com.ivanribes.tareas.controller;

import com.ivanribes.tareas.dto.ListaRequest;
import com.ivanribes.tareas.dto.ListaResponse;
import com.ivanribes.tareas.service.ListaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listas")
@RequiredArgsConstructor
public class ListaController {

    private final ListaService listaService;

    @PostMapping()
    public ResponseEntity<ListaResponse> create(@Valid @RequestBody ListaRequest dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(listaService.crearLista(dto));

    }

    @GetMapping
    public ResponseEntity<List<ListaResponse>> getListas() {

        return ResponseEntity.ok(listaService.listarListas());
    }
}
