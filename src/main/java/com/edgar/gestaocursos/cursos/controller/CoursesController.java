package com.edgar.gestaocursos.cursos.controller;

import com.edgar.gestaocursos.cursos.dto.CourseDTO;
import com.edgar.gestaocursos.cursos.dto.StatusRequestDTO;
import com.edgar.gestaocursos.cursos.entity.CoursesEntity;
import com.edgar.gestaocursos.cursos.usecase.CoursesUseCase;
import com.edgar.gestaocursos.exceptions.CourseNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos", description = "Modulo sobre os cursos")
public class CoursesController {

    @Autowired
    private CoursesUseCase coursesUseCase;
    @PostMapping()
    @Operation(summary = "Criacao de curso", description = "Rota responsavel pela criacao de novos cursos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CoursesEntity.class))
            }),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CoursesEntity courseEntity) {
        try {
            var course = this.coursesUseCase.execute(courseEntity);
            return ResponseEntity.status(HttpStatus.OK).body(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    @Operation(summary = "Listagem de Cursos", description = "Rota responsavel pela listagem de todos os cursos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = CoursesEntity.class)))
            })
    })
    public ResponseEntity<Object> listCourses() {
        try {
            var courses = this.coursesUseCase.listAllCourses();
            return ResponseEntity.status(HttpStatus.OK).body(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alteracao de Curso", description = "Rota responsavel pela alteracao de informacoes sobre o curso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CourseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "The course does not exist")
    })
    public ResponseEntity<Object> updateCourse(@Valid @PathVariable UUID id, @RequestBody CourseDTO courseDTO) {
        try {
            return this.coursesUseCase.updateCourse(id,courseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Alteracao do estado do Curso", description = "Rota responsavel pela alteracao do estado do curso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CoursesEntity.class))
            }),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Object> alterActiveStatus(@PathVariable UUID id, @RequestBody StatusRequestDTO statusRequestDTO) {
        if(statusRequestDTO.getStatus() == null) {
            return ResponseEntity.badRequest().body("The status field is required");
        }
        try {
            return this.coursesUseCase.updateStatus(id, statusRequestDTO.getStatus());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("The value of field 'status' in JSON is invalid" );
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remocao de Curso", description = "rota responsavel por remocao de curso")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "The course does not exist")
    })
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            return this.coursesUseCase.removeCourse(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
