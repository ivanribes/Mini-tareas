package com.ivanribes.tareas.dto;

import java.util.Map;

public record TareaResponse(
        Long id,
        String nombre,
        String descripcion,
        boolean hecha,
        Map<String, Object> datos,
        Long listaId
) {
}
