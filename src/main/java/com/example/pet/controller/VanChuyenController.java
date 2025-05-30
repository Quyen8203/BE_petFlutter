package com.example.pet.controller;

import com.example.pet.dto.UpdateStatusRequest;
import com.example.pet.models.VanChuyen;
import com.example.pet.service.VanChuyenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/van-chuyen")
@CrossOrigin(origins = "*")
public class VanChuyenController {

    @Autowired
    private VanChuyenService vanChuyenService;

    @GetMapping("/nguoidung/{userId}")
    public ResponseEntity<List<VanChuyen>> getVanChuyenByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(vanChuyenService.getVanChuyenByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<VanChuyen>> getAllVanChuyen() {
        return ResponseEntity.ok(vanChuyenService.getAllVanChuyen());
    }

    private static final Logger logger = LoggerFactory.getLogger(VanChuyenController.class);

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVanChuyenStatus(@PathVariable int id, @RequestBody UpdateStatusRequest request) {
        try {
            vanChuyenService.updateVanChuyenStatus(id, request.getStatus());
            return ResponseEntity.ok("Cập nhật trạng thái vận chuyển thành công.");
        } catch (IllegalArgumentException e) {
            logger.error("Lỗi cập nhật trạng thái cho ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Lỗi hệ thống cho ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Lỗi hệ thống: " + e.getMessage());
        }
    }
}