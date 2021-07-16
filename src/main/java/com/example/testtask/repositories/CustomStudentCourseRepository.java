package com.example.testtask.repositories;

import com.example.testtask.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

//Репозиторий, позволяющий обновлять информацию в БД о курсах, проходимых студентами
@Repository
public interface CustomStudentCourseRepository extends JpaRepository<Student, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into journal values (?1, ?2, ?3)", nativeQuery = true)
    void saveCourseForStudent(Long studentId, Long courseId, Long selectedLecturerId);
}
