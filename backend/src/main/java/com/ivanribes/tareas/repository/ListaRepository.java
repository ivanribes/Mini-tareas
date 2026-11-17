package com.ivanribes.tareas.repository;

import com.ivanribes.tareas.dto.ListaResponse;
import com.ivanribes.tareas.entity.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListaRepository extends JpaRepository<Lista, Long>
{
    @Query("""
select new com.ivanribes.tareas.dto.ListaResponse(l.id, l.nombre, count (t.id))
from Lista l left join l.tareas t
group by l.id, l.nombre
""")
    List<ListaResponse> findAllConNumeroTareas();

}
