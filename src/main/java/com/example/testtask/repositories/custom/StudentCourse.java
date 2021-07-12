package com.example.testtask.repositories.custom;

import com.example.testtask.models.Student;

//Интерфейс с методами для сохранения курсов для студентов
public interface StudentCourse {
    void saveAllCoursesForStudent(Student updatedStudent);
    void saveCourseForStudent(Long studentId, Long courseId);
}
