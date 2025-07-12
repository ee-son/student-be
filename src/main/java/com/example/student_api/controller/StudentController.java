package com.example.student_api.controller;

import com.example.student_api.model.Student;
import com.example.student_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// Dependency for documentation
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/students")
@Tag(name = "Student", description = "API untuk mengelola data mahasiswa")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    @Operation(
        summary = "GET semua data mahasiswa", 
        description = "Mengambil seluruh data mahasiswa dari database. Endpoint ini mengembalikan daftar lengkap mahasiswa dalam format JSON.")
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "GET satu data mahasiswa berdasarkan ID",
        description = "Mengambil data mahasiswa berdasarkan ID yang diberikan. Jika ID tidak ditemukan, akan mengembalikan pesan error dengan status 404.")
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
    @Operation(
        summary = "Tambah satu data mahasiswa",
        description = "Menambahkan data mahasiswa baru melalui permintaan pengguna. Data baru akan disimpan dan dikembalikan sebagai konfirmasi berhasil.")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        Student created = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse(true, "Data berhasil ditambahkan", created)
        );
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update satu data mahasiswa berdasarkan ID",
        description = "Memperbarui data mahasiswa berdasarkan ID yang diberikan. Jika ID tidak ditemukan, maka akan mengembalikan pesan gagal dengan status 404.")
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
    @Operation(
        summary = "Hapus satu data mahasiswa berdasarkan ID",
        description = "Menghapus satu data mahasiswa berdasarkan ID yang dikirimkan. Berguna untuk menghapus data yang sudah tidak dibutuhkan.")
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
