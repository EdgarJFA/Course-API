package com.edgar.gestaocursos.cursos.dto;

import com.edgar.gestaocursos.cursos.constants.CourseStatus;
import lombok.Data;

@Data
public class StatusRequestDTO {
    private CourseStatus Status;
}
