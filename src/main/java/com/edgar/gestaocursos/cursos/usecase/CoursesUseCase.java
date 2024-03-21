package com.edgar.gestaocursos.cursos.usecase;

import com.edgar.gestaocursos.cursos.constants.CourseStatus;
import com.edgar.gestaocursos.cursos.dto.CourseDTO;
import com.edgar.gestaocursos.cursos.entity.CoursesEntity;
import com.edgar.gestaocursos.cursos.repository.CoursesRepository;
import com.edgar.gestaocursos.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoursesUseCase {

    @Autowired
    private CoursesRepository coursesRepository;
    public CoursesEntity execute(CoursesEntity course) {
        return this.coursesRepository.save(course);
    }

    public List<CoursesEntity> listAllCourses() {
        return this.coursesRepository.findAll();
    }

    public ResponseEntity<Object>  updateCourse(UUID courseId, CourseDTO courseDTO) {
        Optional<CoursesEntity> result = this.coursesRepository.findById(courseId);
        if(result.isPresent()) {
            try {
                CoursesEntity course = result.get();
                course.setName(courseDTO.getName());
                course.setCategory(courseDTO.getCategory());
                course.setActive(courseDTO.getActive());

                var response = this.coursesRepository.save(course);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            throw new CourseNotFoundException();
        }
    }

    public ResponseEntity<Object> updateStatus(UUID id, CourseStatus status) {
        Optional<CoursesEntity> result = this.coursesRepository.findById(id);
        if (result.isPresent()) {
            CoursesEntity course = result.get();
            switch (status) {
                case ACTIVE -> course.setActive(true);
                case INACTIVE -> course.setActive(false);
            }
            return ResponseEntity.ok().body(this.coursesRepository.save(course));
        } else {
            throw new CourseNotFoundException();
        }
    }

    public ResponseEntity<Object> removeCourse(UUID courseId) {
        Optional<CoursesEntity> course = this.coursesRepository.findById(courseId);
        if(course.isPresent()) {
            this.coursesRepository.deleteById(courseId);
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
        } else {
            throw new CourseNotFoundException();
        }
    }
}
