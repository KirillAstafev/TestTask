package com.example.testtask.services;

import com.example.testtask.models.Course;
import com.example.testtask.models.Lecturer;
import com.example.testtask.models.Student;
import com.example.testtask.repositories.LecturerRepository;
import com.example.testtask.repositories.StudentRepository;
import com.example.testtask.repositories.CustomStudentCourseRepository;
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
        List<Course> currentCourses = updatedStudent.getCourses();
        checkTotalWorkload(currentCourses);

        List<Lecturer> availableLecturers = findAvailableLecturers(currentCourses);

        for (int i = 0; i < currentCourses.size(); i++) {
            studentCourseRepository.saveCourseForStudent(updatedStudent.getStudentId(),
                    currentCourses.get(i).getCourseId(),
                    availableLecturers.get(i).getLecturerId());
        }
    }

    //Метод, проверяющий наличие не перегруженных преподавателей для каждого выбранного студентом курса
    private List<Lecturer> findAvailableLecturers(List<Course> courses) throws Exception {
        //Список свободных преподавателей (один для каждого курса)
        List<Lecturer> resultLecturers = new ArrayList<>();
        List<String> filledCourseNames = new ArrayList<>();

        for (Course course : courses) {
            List<Lecturer> availableLecturers = lecturerRepository.findAvailableLecturersForCourse(course.getCourseId());

            /*
            Добавление названия курса в сообщение об ошибке (в случае, если список свободных преподавателей
            (availableLecturers) пуст
            */
            if (availableLecturers.isEmpty()) {
                filledCourseNames.add(course.getCourseName());
                continue;
            }

            //Добавление преподавателя с минимальной текущей нагрузкой в список свободных
            resultLecturers.add(availableLecturers.get(0));
        }

        if (!filledCourseNames.isEmpty()) {
            StringBuilder builder = new StringBuilder().append("ОШИБКА! ДЛЯ СЛЕДУЮЩИХ КУРСОВ: ");
            for (String courseName : filledCourseNames) {
                builder.append(courseName).append(" ");
            }

            builder.append(" ОТСУТСТВУЮТ СВОБОДНЫЕ ПРЕПОДАВАТЕЛИ! ВЫБЕРИТЕ ДРУГИЕ КУРСЫ!");
            throw new Exception(builder.toString());
        }

        return resultLecturers;
    }

    //Проверка суммарной рабочей нагрузки студента (должна быть не менее 100 часов)
    private void checkTotalWorkload(List<Course> currentCourses) throws Exception {
        int totalWorkload = currentCourses.stream().mapToInt(Course::getWorkLoad).sum();
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
