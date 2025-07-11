package com.example.student_api.controller;

import com.example.student_api.model.Student;
import com.example.student_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse(false, "Data tidak ditemukan")
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        Student created = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse(true, "Data berhasil ditambahkan", created)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        Student updated = studentService.updateStudent(id, updatedStudent);
        if (updated != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Data berhasil diupdate", updated));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse(false, "Data gagal diupdate. ID tidak ditemukan.")
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "Data berhasil dihapus"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse(false, "Data gagal dihapus. ID tidak ditemukan.")
            );
        }
    }
}
