package com.edgar.gestaocursos.cursos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    @NotBlank()
    private String name;
    @NotBlank()
    private String category;
    private Boolean active;
}
