package com.example.testtask.security.details;

import com.example.testtask.repositories.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentUserDetailsServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepository;

    public StudentUserDetailsServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new StudentUserDetailsImpl(studentRepository.findStudentByLogin(s).orElseThrow(RuntimeException::new));
    }
}
