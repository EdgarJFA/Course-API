package com.edgar.gestaocursos.cursos.repository;

import com.edgar.gestaocursos.cursos.entity.CoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoursesRepository extends JpaRepository<CoursesEntity, UUID> {
}
