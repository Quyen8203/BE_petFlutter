package com.example.pet.repository;

import com.example.pet.models.Loai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiRepository extends JpaRepository<Loai, Integer> {
}
