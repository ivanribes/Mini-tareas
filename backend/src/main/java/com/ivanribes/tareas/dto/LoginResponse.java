package com.ivanribes.tareas.dto;

public record LoginResponse(
        String token,
        String tipo,
        long expiresIn
) {
}
