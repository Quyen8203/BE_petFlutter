package com.example.pet.controller;

import com.example.pet.models.SanPham;
import com.example.pet.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {})
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    // Lấy danh sách sản phẩm
    @GetMapping("/list")
    public List<SanPham> getAllSanPham() {
        return sanPhamService.getAllSanPhams();
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/search")
    public SanPham getSanPhamById(@RequestParam int idsp) {
        return sanPhamService.getSanPhamById(idsp).orElse(null);
    }

    

    // Thêm sản phẩm mới
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public SanPham addSanPham(@RequestBody SanPham sanPham) {
        return sanPhamService.addSanPham(sanPham);
    }

    // Cập nhật sản phẩm
    @PutMapping("/up")
    public SanPham updateSanPham(@RequestParam int idsp, @RequestBody SanPham sanPham) {
        System.out.println("Updating product with ID: " + idsp);
        return sanPhamService.updateSanPham(idsp, sanPham);
    }

    // Xóa sản phẩm
    @DeleteMapping("/del")
    public void deleteSanPham(@RequestParam int idsp) {
        sanPhamService.deleteSanPham(idsp);
    }

}
