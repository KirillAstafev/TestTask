package com.example.testtask.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @NotNull
    @Column(name = "student_login", length = 30)
    private String studentLogin;

    @NotNull
    @Column(name = "student_password_hash", length = 80)
    @JsonIgnore
    private String studentPasswordHash;
    
    @NotNull
    @Column(name = "student_forename", length = 50)
    @JsonIgnore
    private String foreName;

    @NotNull
    @Column(name = "student_patronymic", length = 50)
    @JsonIgnore
    private String patronymic;

    @NotNull
    @Column(name = "student_surname", length = 50)
    @JsonIgnore
    private String surName;

    @ManyToMany
    @JoinTable(name = "students_courses",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;
}
