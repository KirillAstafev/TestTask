package com.example.testtask.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "lecturer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private Long lecturerId;

    @NotNull
    @Column(name = "lecturer_forename", length = 50)
    private String foreName;

    @NotNull
    @Column(name = "lecturer_patronymic", length = 50)
    private String patronymic;

    @NotNull
    @Column(name = "lecturer_surname", length = 50)
    private String surName;

    @ManyToMany
    @JoinTable(name = "lecturers_courses",
    joinColumns = @JoinColumn(name = "lecturer_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;
}
