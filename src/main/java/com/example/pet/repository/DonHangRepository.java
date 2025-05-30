package com.example.pet.repository;

import com.example.pet.models.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByNguoidung_Id(int userId);
}

