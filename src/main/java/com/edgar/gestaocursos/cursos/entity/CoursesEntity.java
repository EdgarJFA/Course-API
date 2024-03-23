package com.edgar.gestaocursos.cursos.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "cursos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoursesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank()
    @Length(max = 255, message = "The 'name' field must contain a maximum of 255 characters.")
    @Schema(example = "Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @NotBlank()
    @Schema(example = "Back-end", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;
    private boolean active;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
