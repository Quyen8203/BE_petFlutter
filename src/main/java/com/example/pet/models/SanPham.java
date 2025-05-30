package com.example.pet.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.example.pet.models.DanhMuc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sanpham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsp;
    @Column(nullable = false)
    private String ten; // Tên sản phẩm

    @Column(nullable = false)
    private double dongia; // Giá sản phẩm

    private String mota; // Mô tả (có thể null)

    @Column(nullable = false)
    private int sl;

    @Column(columnDefinition = "LONGTEXT")
    private String hinhanh;

    @ManyToOne
    @JoinColumn(name = "iddm", nullable = false)
    private DanhMuc danhmuc;

    @ManyToMany
    @JoinTable(
            name = "pet_flutter_sanpham_loai",
            joinColumns = @JoinColumn(name = "idsp"),
            inverseJoinColumns = @JoinColumn(name = "idl")
    )
    @JsonIgnore
    private Set<Loai> loais = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SanPham sanPham = (SanPham) o;
        return idsp == sanPham.idsp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idsp);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        return jsonConverter;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public DanhMuc getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(DanhMuc danhmuc) {
        this.danhmuc = danhmuc;
    }

    public Set<Loai> getLoais() {
        return loais;
    }

    public void setLoais(Set<Loai> loais) {
        this.loais = loais;
    }
}