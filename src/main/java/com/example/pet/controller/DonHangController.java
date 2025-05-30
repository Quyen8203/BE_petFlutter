package com.example.pet.controller;

import com.example.pet.models.DonHang;
import com.example.pet.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/don-hang")
@CrossOrigin(origins = "", allowedHeaders = "*", methods = {})
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    @GetMapping
    public ResponseEntity<List<DonHang>> getAllDonHangs() {
        return ResponseEntity.ok(donHangService.getAllDonHangs());
    }

    @GetMapping("/nguoidung/{userId}")
    public ResponseEntity<List<DonHang>> getDonHangsByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(donHangService.getDonHangsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonHang> getDonHangById(@PathVariable int id) {
        Optional<DonHang> donHang = donHangService.getDonHangById(id);
        return donHang.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DonHang> addDonHang(@RequestBody DonHang donHang) {
        return ResponseEntity.ok(donHangService.addDonHang(donHang));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonHang(@PathVariable int id) {
        donHangService.deleteDonHang(id);
        return ResponseEntity.ok().build();
    }
}