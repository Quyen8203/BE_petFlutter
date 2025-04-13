package com.example.pet.repository;

import com.example.pet.models.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DanhMucRepository extends JpaRepository<DanhMuc, String> {
}
