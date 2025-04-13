package com.example.pet.service;

import com.example.pet.models.GioHang;
import com.example.pet.models.NguoiDung;
import com.example.pet.models.SanPham;
import com.example.pet.repository.GioHangRepository;
import com.example.pet.repository.NguoiDungRepository;
import com.example.pet.repository.SanPhamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;



    public List<GioHang> getGioHangByUser(int userId) {
        return gioHangRepository.findByNguoidung_Id(userId);
    }

    public void deleteByUser(int userId) {
        gioHangRepository.deleteByNguoidung_Id(userId);
    }

    public GioHang save(GioHang gioHang) {
        return gioHangRepository.save(gioHang);
    }

    public GioHang updateQuantity(int cartId, int quantity) {
        GioHang item = gioHangRepository.findById(cartId).orElseThrow();
        item.setSoluong(quantity);
        return gioHangRepository.save(item);
    }

    public void deleteGioHangById(int id) {
        gioHangRepository.deleteById(id);
    }

    @Transactional
    public void xoaTheoNguoiDungId(int nguoidungId) {
        gioHangRepository.deleteByNguoidungId(nguoidungId);
    }


}
