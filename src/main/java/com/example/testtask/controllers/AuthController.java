package com.example.testtask.controllers;

import com.example.testtask.auth.AuthRequest;
import com.example.testtask.models.Student;
import com.example.testtask.services.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
    Аутентификация студента. Имя пользователя и пароль передаются в метод в объекте AuthRequest,
    результат - объект Student, представляющий аутентифицированного студента
     */
    @PostMapping(path = "/login", produces = "application/json", consumes = "application/json")
    public @ResponseBody Student getAuthStudent(@RequestBody AuthRequest authRequest) {
        return studentService.getStudentByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
    }
}
