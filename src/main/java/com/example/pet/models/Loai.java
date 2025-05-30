package com.example.pet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loai")
public class Loai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idl;

    private String huongvi;
    private String sokg;
    private double gia;

    @ManyToMany(mappedBy = "loais")
    @JsonIgnore
    private Set<SanPham> sanPhams = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loai loai = (Loai) o;
        return idl == loai.idl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idl);
    }

    public int getIdl() {
        return idl;
    }

    public void setIdl(int idl) {
        this.idl = idl;
    }

    public String getHuongvi() {
        return huongvi;
    }

    public void setHuongvi(String huongvi) {
        this.huongvi = huongvi;
    }

    public String getSokg() {
        return sokg;
    }

    public void setSokg(String sokg) {
        this.sokg = sokg;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public Set<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(Set<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }
}
