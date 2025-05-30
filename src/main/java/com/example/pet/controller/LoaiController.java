package com.example.pet.controller;

import com.example.pet.models.Loai;
import com.example.pet.service.LoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loai")
@CrossOrigin(origins = "", allowedHeaders = "*", methods = {})
public class LoaiController {
    @Autowired
    private LoaiService loaiService;

    @GetMapping("/list")
    public List<Loai> getAllLoai() {
        return loaiService.getAllLoai();
    }

    @GetMapping("/by-sanpham/{idsp}")
    public List<Loai> getLoaiBySanPham(@PathVariable int idsp) {
        return loaiService.getAllLoai().stream()
                .filter(loai -> loai.getSanPhams().stream()
                        .anyMatch(sanPham -> sanPham.getIdsp() == idsp))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Loai addLoai(@RequestBody Loai loai) {
        return loaiService.addLoai(loai);
    }

    @PutMapping("/up")
    public ResponseEntity<Loai> updateLoai(@RequestParam int id, @RequestBody Loai loai) {
        Loai updatedLoai = loaiService.updateLoai(id, loai);
        if (updatedLoai == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedLoai);
    }

    @DeleteMapping("/del")
    public ResponseEntity<Void> deleteLoai(@RequestParam int id) {
        loaiService.deleteLoai(id);
        return ResponseEntity.noContent().build();
    }
}
