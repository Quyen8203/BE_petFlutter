package com.example.pet.controller;

import com.example.pet.models.DanhMuc;
import com.example.pet.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhmuc")
@CrossOrigin(origins = "*", allowedHeaders = "*",methods = {})
public class DanhMucController {

    @Autowired
    private DanhMucService danhMucService;

    // Lấy danh sách tất cả danh mục
    @GetMapping("/list")
    public List<DanhMuc> getAllDanhMucs() {
        return danhMucService.getAllDanhMucs();
    }

    // Thêm danh mục mới
    @PostMapping("/add")
    public ResponseEntity<?> addDanhMuc(@RequestBody DanhMuc danhMuc) {
        try {
            DanhMuc savedDanhMuc = danhMucService.addDanhMuc(danhMuc);
            return ResponseEntity.status(201).body(savedDanhMuc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Cập nhật danh mục
    @PutMapping("/up")
    public ResponseEntity<?> updateDanhMuc(@RequestParam String iddm, @RequestBody DanhMuc danhMuc) {
        try {
            DanhMuc updatedDanhMuc = danhMucService.updateDanhMuc(iddm, danhMuc);
            if (updatedDanhMuc == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedDanhMuc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Xóa danh mục
    @DeleteMapping("/del/{iddm}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable String iddm) {
        danhMucService.deleteDanhMuc(iddm);
        return ResponseEntity.ok().build();
    }
}