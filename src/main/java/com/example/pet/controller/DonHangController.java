package com.example.pet.controller;

import com.example.pet.models.DonHang;
import com.example.pet.service.ChiTieuService;
import com.example.pet.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/don-hang")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<List<DonHang>> getAllDonHangs() {
        return ResponseEntity.ok(donHangService.getAllDonHangs());
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<DonHang> getDonHangById(@PathVariable int id) {
        return donHangService.getDonHangById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("up/{id}")
    public ResponseEntity<DonHang> updateDonHang(@PathVariable int id, @RequestBody DonHang donHang) {
        donHang.setId(id);
        DonHang updated = donHangService.updateDonHang(donHang);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<DonHang> createDonHang(@RequestBody DonHang donHang) {
        DonHang saved = donHangService.addDonHang(donHang);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }



}

