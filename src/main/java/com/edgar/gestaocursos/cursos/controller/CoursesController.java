package com.edgar.gestaocursos.cursos.controller;

import com.edgar.gestaocursos.cursos.constants.CourseStatus;
import com.edgar.gestaocursos.cursos.dto.CourseDTO;
import com.edgar.gestaocursos.cursos.entity.CoursesEntity;
import com.edgar.gestaocursos.cursos.usecase.CoursesUseCase;
import com.edgar.gestaocursos.exceptions.CourseNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cursos")
public class CoursesController {

    @Autowired
    private CoursesUseCase coursesUseCase;
    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody CoursesEntity courseEntity) {
        try {
            var course = this.coursesUseCase.execute(courseEntity);
            return ResponseEntity.status(HttpStatus.OK).body(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> listCourses() {
        try {
            var courses = this.coursesUseCase.listAllCourses();
            return ResponseEntity.status(HttpStatus.OK).body(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@Valid @PathVariable UUID id, @RequestBody CourseDTO courseDTO) {
        try {
            return this.coursesUseCase.updateCourse(id,courseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Object> alterActiveStatus(@PathVariable UUID id, @RequestBody Map<String, String> request) {
        String statusValue = request.get("status");
        if(statusValue == null) {
            return ResponseEntity.badRequest().body("The status field is required");
        }
        CourseStatus status;
        try {
            status = CourseStatus.valueOf(statusValue);
            return this.coursesUseCase.updateStatus(id, status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("The value of field 'status' in JSON is invalid" );
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            return this.coursesUseCase.removeCourse(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
