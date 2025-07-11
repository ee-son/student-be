package com.example.student_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Student {
    @Id
    private String id;

    private String namaDepan;
    private String namaBelakang;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalLahir;

    // Getter & Setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNamaDepan() {
        return namaDepan;
    }
    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }
    public String getNamaBelakang() {
        return namaBelakang;
    }
    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }
    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }
    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
