package com.example.testtask.services;

import com.example.testtask.models.Course;
import com.example.testtask.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.getAllCourses();
    }
}
