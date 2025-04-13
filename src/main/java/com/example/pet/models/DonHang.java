package com.example.pet.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donhang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddon")
    private int id;



    @Column(name = "tenkh")
    private String tenkh;

    @Column(name = "tensp")
    private String tensp;

    @Column(name = "soluong")
    private int soluong;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "thanhtien")
    private double thanhtien;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngay_dat_hang")
    private Date ngay_dat_hang;

    @Column(name = "trangThai")
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private NguoiDung nguoidung;

    @ManyToOne
    @JoinColumn(name = "idsp", nullable = false)
    private SanPham sanpham;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(double thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Date getNgay_dat_hang() {
        return ngay_dat_hang;
    }

    public void setNgay_dat_hang(Date ngay_dat_hang) {
        this.ngay_dat_hang = ngay_dat_hang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public NguoiDung getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(NguoiDung nguoidung) {
        this.nguoidung = nguoidung;
    }

    public SanPham getSanpham() {
        return sanpham;
    }

    public void setSanpham(SanPham sanpham) {
        this.sanpham = sanpham;
    }
}