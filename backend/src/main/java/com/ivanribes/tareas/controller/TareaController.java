package com.ivanribes.tareas.controller;

import com.ivanribes.tareas.dto.TareaResponse;
import com.ivanribes.tareas.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;


@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public ResponseEntity<Page<TareaResponse>> getTareas(@RequestParam(required = false) Long listaId,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(tareaService.listar(listaId, pageable));
    }

}
