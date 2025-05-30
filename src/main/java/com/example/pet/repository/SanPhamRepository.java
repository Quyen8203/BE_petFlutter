package com.example.pet.repository;

import com.example.pet.models.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    List<SanPham> findByTenContainingIgnoreCase(String ten);
    List findByDanhmucIddm(String iddm);

    @Query("SELECT sp FROM SanPham sp LEFT JOIN FETCH sp.loais WHERE sp.idsp = :idsp")
    Optional<SanPham> findByIdWithLoais(@Param("idsp") int idsp);

    @Query("SELECT sp FROM SanPham sp LEFT JOIN FETCH sp.loais")
    List<SanPham> findAllWithLoais();
}
