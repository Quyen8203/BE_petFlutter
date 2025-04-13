package com.example.pet.repository;

import com.example.pet.models.ChiTieu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTieuRepository extends JpaRepository<ChiTieu, Integer> {
    List<ChiTieu> findByUserId(int userId);
}
