package com.example.pet.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;



@Entity
@Data
@Table(name = "danhmuc")
public class DanhMuc {
    @Id
    @Column(nullable = false)
    private String iddm;

    private String ten;

    public DanhMuc() {
    }

    public DanhMuc(String iddm, String ten) {
        this.iddm = iddm;
        this.ten = ten;
    }

    public String getIddm() {
        return iddm;
    }

    public void setIddm(String iddm) {
        this.iddm = iddm;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}