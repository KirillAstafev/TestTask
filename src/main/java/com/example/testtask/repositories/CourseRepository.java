package com.example.testtask.repositories;

import com.example.testtask.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c")
    List<Course> getAllCourses();

    @Query("select c from Course c join fetch Student s where s.studentId=:studentId")
    List<Course> getAllStudentCourses(String studentId);
}
