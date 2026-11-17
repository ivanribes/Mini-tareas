package com.ivanribes.tareas.service;

import com.ivanribes.tareas.dto.ListaRequest;
import com.ivanribes.tareas.dto.ListaResponse;
import com.ivanribes.tareas.entity.Lista;
import com.ivanribes.tareas.repository.ListaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaService {

    private final ListaRepository listaRepository;

    @Transactional
    public ListaResponse crearLista(ListaRequest request) {

        Lista lista = new Lista();
        lista.setNombre(request.nombre());
        lista = listaRepository.save(lista);

        return new ListaResponse(lista.getId(), lista.getNombre(), (long)lista.getTareas().size());
    }

    @Transactional (readOnly = true)
    public List<ListaResponse> listarListas() {
        return listaRepository.findAllConNumeroTareas();
    }
}
