package com.example.testtask.repositories;

import com.example.testtask.models.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    /*
    Запрос, извлекающий из БД всех преподавателей, преподающих курс, максимальная нагрузка которых (20 студентов)
    ещё не достигнута. Результат сортируется в порядке возрастания текущей нагрузки на преподавателей
     */
    @Query(value = "select distinct lecturer.lecturer_id, lecturer_forename, lecturer_patronymic, lecturer_surname " +
            "from lecturer inner join lecturers_courses lc on lecturer.lecturer_id = lc.lecturer_id " +
            "inner join course c on lc.course_id = c.course_id " +
            "left join journal j on lecturer.lecturer_id = j.lecturer_id " +
            "where c.course_id=:courseId " +
            "group by student_id, lecturer.lecturer_id, lecturer_forename, lecturer_patronymic, lecturer_surname " +
            "having count(*) < 20",
            nativeQuery = true)
    List<Lecturer> findAvailableLecturersForCourse(Long courseId);
}
