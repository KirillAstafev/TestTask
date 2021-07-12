package com.example.testtask.repositories.custom;

import com.example.testtask.models.Course;
import com.example.testtask.models.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/*
Реализация интерфейса StudentCourse.
Для работы с БД используется entityManager (нативные запросы составляются вручную)
 */

public class StudentCourseImpl implements StudentCourse {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveAllCoursesForStudent(Student updatedStudent) {
        for (Course course : updatedStudent.getCourses()) {
            saveCourseForStudent(updatedStudent.getStudentId(), course.getCourseId());
        }
    }

    @Override
    public void saveCourseForStudent(Long studentId, Long courseId) {
        Query query = entityManager.createNativeQuery("insert into students_courses values (?1, ?2)");
        query.setParameter(1, studentId);
        query.setParameter(2, courseId);

        query.executeUpdate();
    }
}
