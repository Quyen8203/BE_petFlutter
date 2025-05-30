package com.example.pet.controller;

import com.example.pet.models.GioHang;
import com.example.pet.repository.GioHangRepository;
import com.example.pet.service.GioHangService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giohang")
@CrossOrigin(origins = "", allowedHeaders = "*", methods = {})
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private GioHangRepository gioHangRepository;

    @GetMapping("/user/{id}")
    public List<GioHang> getGioHangByUserId(@PathVariable int id) {
        return gioHangService.getGioHangByUser(id);
    }

    @PostMapping("/add")
    public ResponseEntity<GioHang> addToCart(@RequestBody GioHang gioHang) {
        // Kiểm tra xem Loai có tồn tại và hợp lệ không
        if (gioHang.getLoai() == null || gioHang.getLoai().getIdl() <= 0) {
            return ResponseEntity.badRequest().body(null); // Trả về lỗi nếu không có Loai
        }
        // Kiểm tra xem SanPham có tồn tại không
        if (gioHang.getSanpham() == null || gioHang.getSanpham().getIdsp() <= 0) {
            return ResponseEntity.badRequest().body(null); // Trả về lỗi nếu không có SanPham
        }
        GioHang savedGioHang = gioHangService.save(gioHang);
        return ResponseEntity.ok(savedGioHang);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuantity(@PathVariable int id, @RequestParam int quantity) {
        gioHangService.updateQuantity(id, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGioHangById(@PathVariable int id) {
        gioHangService.deleteGioHangById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/xoa-theo-nguoidung/{userId}")
    @Transactional
    public ResponseEntity<?> xoaGioHangTheoNguoiDung(@PathVariable int userId) {
        gioHangRepository.deleteByNguoidungId(userId);
        return ResponseEntity.ok().build();
    }

}
