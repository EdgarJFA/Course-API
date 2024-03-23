package com.edgar.gestaocursos.cursos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    @NotBlank()
    @Schema(example = "ReactJs", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @NotBlank()
    @Schema(example = "Front-end", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;
    private Boolean active;
}
