package com.example.pet.service;

import com.example.pet.models.DonHang;
import com.example.pet.models.NguoiDung;
import com.example.pet.models.SanPham;
import com.example.pet.repository.DonHangRepository;
import com.example.pet.repository.NguoiDungRepository;
import com.example.pet.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonHangService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private VanChuyenService vanChuyenService;

    public List<DonHang> getAllDonHangs() {
        return donHangRepository.findAll();
    }

    public Optional<DonHang> getDonHangById(int id) {
        return donHangRepository.findById(id);
    }

    public List<DonHang> getDonHangsByUserId(int userId) {
        return donHangRepository.findByNguoidung_Id(userId);
    }

    public DonHang addDonHang(DonHang donHang) {
        NguoiDung nguoidung = nguoiDungRepository.findById(donHang.getNguoidung().getId())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        SanPham sanpham = sanPhamRepository.findById(donHang.getSanpham().getIdsp())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        donHang.setNguoidung(nguoidung);
        donHang.setSanpham(sanpham);
        donHang.setNgay_dat_hang(new Date());

        DonHang savedDonHang = donHangRepository.save(donHang);
        vanChuyenService.createVanChuyenForDonHang(savedDonHang);

        return savedDonHang;
    }

    public void deleteDonHang(int id) {
        donHangRepository.deleteById(id);
    }
}