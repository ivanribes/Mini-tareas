package com.ivanribes.tareas.repository;

import com.ivanribes.tareas.entity.Tarea;
import org.springframework.data.jpa.domain.Specification;

public class TareaSpecification {

    public static Specification<Tarea> perteneceALista(Long listaId) {
        if (listaId==null) {
            return Specification.unrestricted();
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lista").get("id"), listaId);
    }
}
