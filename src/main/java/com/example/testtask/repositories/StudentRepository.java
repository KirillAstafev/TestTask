package com.example.testtask.repositories;

import com.example.testtask.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where s.studentId=:studentId")
    Optional<Student> findStudentById(Long studentId);

    @Query("select s from Student s where s.studentLogin=:studentLogin")
    Optional<Student> findStudentByLogin(String studentLogin);
}
