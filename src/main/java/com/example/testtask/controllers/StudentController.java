package com.example.testtask.controllers;

import com.example.testtask.models.Student;
import com.example.testtask.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
    Сохранение выбранных студентом курсов
    updatedStudent - объект, представляющий студента (с обновлённым списком курсов)

    Результат - строка с с выводимым пользователю сообщением
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> saveStudentCourses(@RequestBody Student updatedStudent) {
        try {
            studentService.saveAllCoursesForStudent(updatedStudent);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("ПОДПИСКА НА КУРСЫ УСПЕШНО ОФОРМЛЕНА", HttpStatus.OK);
    }
}
