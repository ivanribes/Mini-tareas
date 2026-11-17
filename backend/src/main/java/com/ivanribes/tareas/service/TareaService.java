package com.ivanribes.tareas.service;

import com.ivanribes.tareas.dto.TareaResponse;
import com.ivanribes.tareas.entity.Tarea;
import com.ivanribes.tareas.repository.ListaRepository;
import com.ivanribes.tareas.repository.TareaRepository;
import com.ivanribes.tareas.repository.TareaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ListaRepository listaRepository;

    @Transactional(readOnly = true)
    public Page<TareaResponse> listar(Long listaId, Pageable pageable) {

        if (listaId != null && !listaRepository.existsById(listaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no encontrada");
        }

        return tareaRepository.findAll(TareaSpecification.perteneceALista(listaId), pageable).map(this::toResponse);
    }

    private TareaResponse toResponse(Tarea tarea) {

        return new TareaResponse(tarea.getId(), tarea.getNombre(), tarea.getDescripcion(), tarea.isHecha(),
                tarea.getDatos(), tarea.getLista() != null ? tarea.getLista().getId() : null);
    }
}
