package com.example.pet.repository;

import com.example.pet.models.VanChuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VanChuyenRepository extends JpaRepository<VanChuyen, Integer> {
    List<VanChuyen> findByDonHang_Nguoidung_Id(int userId);
    List<VanChuyen> findByTrangThaiNot(String trangThai);
}