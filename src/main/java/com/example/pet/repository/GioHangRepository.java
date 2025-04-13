package com.example.pet.repository;

import com.example.pet.models.GioHang;
import com.example.pet.models.NguoiDung;
import com.example.pet.models.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    List<GioHang> findByNguoidung_Id(int userId);
    void deleteByNguoidung_Id(int userId);
    void deleteByNguoidungId(int id);


}
