package com.example.pet.controller;

import com.example.pet.dto.SanPhamDTO;
import com.example.pet.models.SanPham;
import com.example.pet.service.SanPhamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/list")
    public List<SanPhamDTO> getAllSanPham() {
        return sanPhamService.getAllSanPhams();
    }

    @GetMapping("/search")
    public ResponseEntity<SanPhamDTO> getSanPhamById(@RequestParam int idsp) {
        return sanPhamService.getSanPhamById(idsp)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSanPham(
            @RequestPart("sanPham") String sanPhamJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SanPham sanPham;
            try {
                sanPham = objectMapper.readValue(sanPhamJson, SanPham.class);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Định dạng JSON không hợp lệ: " + e.getMessage());
            }

            if (sanPham.getTen() == null || sanPham.getDongia() == 0 || sanPham.getSl() == 0 || sanPham.getDanhmuc() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thiếu các trường bắt buộc: ten, dongia, sl, hoặc danhmuc");
            }

            if (file != null && !file.isEmpty()) {
                if (!file.getContentType().startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File phải là ảnh (JPEG, PNG, v.v.)");
                }
                if (file.getSize() > 20 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kích thước file ảnh không được vượt quá 20MB");
                }

                byte[] fileBytes = file.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
                sanPham.setHinhanh(base64Image);
            }

            SanPham newSanPham = sanPhamService.addSanPham(sanPham);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSanPham);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi xử lý yêu cầu: " + e.getMessage());
        }
    }

    @PutMapping(value = "/up", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSanPham(
            @RequestParam int idsp,
            @RequestPart("sanPham") String sanPhamJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SanPham sanPham;
            try {
                sanPham = objectMapper.readValue(sanPhamJson, SanPham.class);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Định dạng JSON không hợp lệ: " + e.getMessage());
            }

            SanPhamDTO existingSanPhamDTO = sanPhamService.getSanPhamById(idsp)
                    .orElse(null);
            if (existingSanPhamDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm");
            }

            SanPham existingSanPham = new SanPham(
                    existingSanPhamDTO.getIdsp(),
                    existingSanPhamDTO.getTen(),
                    existingSanPhamDTO.getDongia(),
                    existingSanPhamDTO.getMota(),
                    existingSanPhamDTO.getSl(),
                    existingSanPhamDTO.getHinhanh(),
                    existingSanPhamDTO.getDanhmuc(),
                    existingSanPhamDTO.getLoai() != null ? existingSanPhamDTO.getLoai() : Collections.emptySet()
            );

            if (file != null && !file.isEmpty()) {
                if (!file.getContentType().startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File phải là ảnh (JPEG, PNG, v.v.)");
                }
                if (file.getSize() > 20 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kích thước file ảnh không được vượt quá 20MB");
                }

                byte[] fileBytes = file.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
                sanPham.setHinhanh(base64Image);
            } else {
                sanPham.setHinhanh(existingSanPham.getHinhanh());
            }

            SanPham updatedSanPham = sanPhamService.updateSanPham(idsp, sanPham);
            if (updatedSanPham == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm");
            }
            return ResponseEntity.ok(updatedSanPham);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi xử lý yêu cầu: " + e.getMessage());
        }
    }

    @DeleteMapping("/del")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSanPham(@RequestParam int idsp) {
        sanPhamService.deleteSanPham(idsp);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sanPhamId}/loai/{loaiId}")
    public ResponseEntity<SanPham> addLoaiToSanPham(@PathVariable int sanPhamId, @PathVariable int loaiId) {
        SanPham updatedSanPham = sanPhamService.addLoaiToSanPham(sanPhamId, loaiId);
        if (updatedSanPham == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSanPham);
    }
}