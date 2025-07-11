package com.example.student_api.service;

import com.example.student_api.model.Student;
import com.example.student_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);
    }

    public Student createStudent(Student student) {
        student.setId(generateNextId());
        return studentRepository.save(student);
    }

    public Student updateStudent(String id, Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setNamaDepan(updatedStudent.getNamaDepan());
            student.setNamaBelakang(updatedStudent.getNamaBelakang());
            student.setTanggalLahir(updatedStudent.getTanggalLahir());
            return studentRepository.save(student);
        }
        return null;
    }

    public boolean deleteStudent(String id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String generateNextId() {
        List<Student> students = studentRepository.findAll();

        int max = students.stream()
                .map(s -> s.getId().replaceAll("[^0-9]", ""))
                .mapToInt(num -> {
                    try {
                        return Integer.parseInt(num);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        return String.format("S%03d", max + 1);
    }
}
