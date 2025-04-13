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
@CrossOrigin(origins = "*")
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
    public GioHang addToCart(@RequestBody GioHang gioHang) {
        return gioHangService.save(gioHang);
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
