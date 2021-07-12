package com.example.testtask.services;

import com.example.testtask.models.Course;
import com.example.testtask.models.Lecturer;
import com.example.testtask.models.Student;
import com.example.testtask.repositories.LecturerRepository;
import com.example.testtask.repositories.StudentRepository;
import com.example.testtask.repositories.custom.CustomStudentCourseRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CustomStudentCourseRepository studentCourseRepository;
    private final PasswordEncoder passwordEncoder;
    private final LecturerRepository lecturerRepository;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, LecturerRepository lecturerRepository, CustomStudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.lecturerRepository = lecturerRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    public void saveAllCoursesForStudent(Student updatedStudent) throws Exception {
        checkTotalWorkload(updatedStudent);
        checkForAvailableLecturers(updatedStudent);

        studentCourseRepository.saveAllCoursesForStudent(updatedStudent);
    }

    //Метод, проверяющий наличие не перегруженных преподавателей для каждого выбранного студентом курса
    private void checkForAvailableLecturers(Student updatedStudent) throws Exception {
        boolean noLecturers = false;

        /*
        Сообщение, выводимое пользователю в том случае, если для какого-то из указанных курсов
        не осталось свободных преподавателей. Сообщение строится по мере поиска преподавателей,
        чтобы вывести наиболее информативное сообщение (обо всех недоступных курсах)
        */
        StringBuilder builder = new StringBuilder().append("ОШИБКА! ДЛЯ СЛЕДУЮЩИХ КУРСОВ: ");

        //Список свободных преподавателей (один для каждого курса)
        List<Lecturer> resultLecturers = new ArrayList<>();

        for (Course course : updatedStudent.getCourses()) {
            List<Lecturer> availableLecturers = lecturerRepository.findAvailableLecturersForCourse(course.getCourseId());

            /*
            Добавление названия курса в сообщение об ошибке (в случае, если список свободных преподавателей
            (availableLecturers) пуст
            */
            if (availableLecturers.isEmpty()) {
                if (!noLecturers) noLecturers = true;
                builder.append(course.getCourseName()).append(" ");
                continue;
            }

            //Добавление преподавателя с минимальной текущей нагрузкой в список свободных
            resultLecturers.add(availableLecturers.get(0));
        }

        if (noLecturers) {
            builder.append(" ОТСУТСТВУЮТ СВОБОДНЫЕ ПРЕПОДАВАТЕЛИ! ВЫБЕРИТЕ ДРУГИЕ КУРСЫ!");
            throw new Exception(builder.toString());
        }

        resultLecturers.forEach(lecturer -> lecturer.setCurrentStudentCount(lecturer.getCurrentStudentCount() + 1));
    }

    //Проверка суммарной рабочей нагрузки студента (должна быть не менее 100 часов)
    private void checkTotalWorkload(Student updatedStudent) throws Exception {
        int totalWorkload = updatedStudent.getCourses().stream().mapToInt(Course::getWorkLoad).sum();
        if (totalWorkload < 100)
            throw new Exception("СЛИШКОМ МАЛАЯ НАГРУЗКА! МИНИМАЛЬНАЯ ТРЕБУЕМАЯ НАГРУЗКА - 100 ЧАСОВ!");
    }

    public Student getStudentByLoginAndPassword(String login, String password) {
        Student student = studentRepository.findStudentByLogin(login).orElseThrow(IllegalArgumentException::new);
        if (passwordEncoder.matches(password, student.getStudentPasswordHash()))
            return student;

        throw new RuntimeException();
    }
}
