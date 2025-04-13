package com.example.pet.service;

import com.example.pet.models.SanPham;
import com.example.pet.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    // Lấy danh sách tất cả sản phẩm
    public List<SanPham> getAllSanPhams() {
        return sanPhamRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<SanPham> getSanPhamById(int idsp) {
        return sanPhamRepository.findById(idsp);
    }

    // Tìm sản phẩm theo tên
    public List<SanPham> getSanPhamByName(String name) {
        return sanPhamRepository.findByTenContainingIgnoreCase(name);
    }

    // Thêm sản phẩm mới
    public SanPham addSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    // Cập nhật sản phẩm
    public SanPham updateSanPham(int idsp, SanPham sanPhamDetails) {
        return sanPhamRepository.findById(idsp)
                .map(sanPham -> {
                    sanPham.setTen(sanPhamDetails.getTen());
                    sanPham.setMota(sanPhamDetails.getMota());
                    sanPham.setDongia(sanPhamDetails.getDongia());
                    sanPham.setSl(sanPhamDetails.getSl());
                    sanPham.setHinhanh(sanPhamDetails.getHinhanh());
                    return sanPhamRepository.save(sanPham);
                })
                .orElse(null);
    }

    // Xóa sản phẩm theo ID
    public void deleteSanPham(int idsp) {
        sanPhamRepository.deleteById(idsp);
    }
}
