package com.edgar.gestaocursos.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException () {
        super("The course does not exist");
    }
}
