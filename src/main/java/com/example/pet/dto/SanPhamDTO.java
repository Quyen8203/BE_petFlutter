package com.example.pet.dto;

import com.example.pet.models.DanhMuc;
import com.example.pet.models.Loai;
import java.util.Set;

public class SanPhamDTO {
    private int idsp;
    private String ten;
    private double dongia;
    private String mota;
    private int sl;
    private String hinhanh;
    private DanhMuc danhmuc;
    private Set<Loai> loai;

    // Constructor
    public SanPhamDTO(int idsp, String ten, double dongia, String mota, int sl, String hinhanh, DanhMuc danhmuc, Set<Loai> loai) {
        this.idsp = idsp;
        this.ten = ten;
        this.dongia = dongia;
        this.mota = mota;
        this.sl = sl;
        this.hinhanh = hinhanh;
        this.danhmuc = danhmuc;
        this.loai = loai;
    }

    // Getters and setters
    public int getIdsp() { return idsp; }
    public String getTen() { return ten; }
    public double getDongia() { return dongia; }
    public String getMota() { return mota; }
    public int getSl() { return sl; }
    public String getHinhanh() { return hinhanh; }
    public DanhMuc getDanhmuc() { return danhmuc; }
    public Set<Loai> getLoai() { return loai; }

    public void setIdsp(int idsp) { this.idsp = idsp; }
    public void setTen(String ten) { this.ten = ten; }
    public void setDongia(double dongia) { this.dongia = dongia; }
    public void setMota(String mota) { this.mota = mota; }
    public void setSl(int sl) { this.sl = sl; }
    public void setHinhanh(String hinhanh) { this.hinhanh = hinhanh; }
    public void setDanhmuc(DanhMuc danhmuc) { this.danhmuc = danhmuc; }
    public void setLoai(Set<Loai> loai) { this.loai = loai; }
}