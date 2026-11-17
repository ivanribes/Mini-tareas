package com.ivanribes.tareas.repository;

import com.ivanribes.tareas.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TareaRepository extends JpaRepository<Tarea, Long>,
        JpaSpecificationExecutor<Tarea> {

}
