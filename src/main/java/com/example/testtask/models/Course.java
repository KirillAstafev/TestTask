package com.example.testtask.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @JsonIgnore
    private Long courseId;

    @NotNull
    @Column(name = "course_name", length = 50)
    private String courseName;

    @NotNull
    @Column(name = "course_workload")
    private Integer workLoad;

    @JsonBackReference
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;
}
