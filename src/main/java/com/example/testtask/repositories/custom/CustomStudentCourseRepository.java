package com.example.testtask.repositories.custom;

import com.example.testtask.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Репозиторий, позволяющий обновлять информацию в БД о курсах, проходимых студентами
@Repository
public interface CustomStudentCourseRepository extends JpaRepository<Student, Long>, StudentCourse {
}
