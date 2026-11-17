package com.ivanribes.tareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record ListaRequest(
        @NotBlank @Size(max = 50) String nombre
) {
}
