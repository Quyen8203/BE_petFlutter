package com.example.pet.repository;

import com.example.pet.models.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    List<SanPham> findByTenContainingIgnoreCase(String ten);
}
