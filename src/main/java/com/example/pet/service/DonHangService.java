package com.example.pet.service;

import com.example.pet.models.DonHang;
import com.example.pet.models.NguoiDung;
import com.example.pet.models.SanPham;
import com.example.pet.repository.DonHangRepository;
import com.example.pet.repository.NguoiDungRepository;
import com.example.pet.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonHangService {

    @Autowired
    private ChiTieuService chiTieuService;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;

    public DonHang updateDonHang(DonHang donHang) {
        DonHang updated = donHangRepository.save(donHang);

        if ("Thành Công".equalsIgnoreCase(donHang.getTrangThai())) {
            chiTieuService.saveFromDonHang(donHang);
        }

        return updated;
    }


    @Autowired
    private DonHangRepository donHangRepository;

    public List<DonHang> getAllDonHangs() {
        return donHangRepository.findAll();
    }

    public Optional<DonHang> getDonHangById(int id) {
        return donHangRepository.findById(id);
    }

    public DonHang addDonHang(DonHang donHang) {
        NguoiDung nguoidung = nguoiDungRepository.findById(donHang.getNguoidung().getId()).orElse(null);
        SanPham sanpham = sanPhamRepository.findById(donHang.getSanpham().getIdsp()).orElse(null);

        if (nguoidung == null || sanpham == null) {
            throw new RuntimeException("Người dùng hoặc sản phẩm không tồn tại!");
        }

        donHang.setNguoidung(nguoidung);
        donHang.setSanpham(sanpham);

        //Nếu thanh toán thành công → trừ kho
        if ("Thành Công".equalsIgnoreCase(donHang.getTrangThai())) {
            if (sanpham.getSl() >= donHang.getSoluong()) {
                sanpham.setSl(sanpham.getSl() - donHang.getSoluong());
                sanPhamRepository.save(sanpham); // cập nhật số lượng còn lại
            } else {
                throw new RuntimeException("Sản phẩm không đủ số lượng trong kho!");
            }
        }

        DonHang saved = donHangRepository.save(donHang);

        // ✅ Lưu chi tiêu nếu thành công
        if ("Thành Công".equalsIgnoreCase(saved.getTrangThai())) {
            chiTieuService.saveFromDonHang(saved);
        }

        return saved;
    }


    public void deleteDonHang(int id) {
        donHangRepository.deleteById(id);
    }
}
