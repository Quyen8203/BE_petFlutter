package com.example.pet.service;

import com.example.pet.models.DanhMuc;
import com.example.pet.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    public List<DanhMuc> getAllDanhMucs() {
        return danhMucRepository.findAll();
    }

    public DanhMuc addDanhMuc(DanhMuc danhMuc) {
        // Kiểm tra xem iddm đã tồn tại chưa
        if (danhMuc.getIddm() == null || danhMuc.getIddm().isEmpty()) {
            throw new IllegalArgumentException("ID danh mục không được để trống");
        }
        if (danhMucRepository.existsById(danhMuc.getIddm())) {
            throw new IllegalArgumentException("ID danh mục đã tồn tại: " + danhMuc.getIddm());
        }
        return danhMucRepository.save(danhMuc);
    }

    public DanhMuc updateDanhMuc(String iddm, DanhMuc danhMuc) {
        // Kiểm tra xem danh mục với iddm cũ có tồn tại không
        Optional<DanhMuc> existingDanhMucOpt = danhMucRepository.findById(iddm);
        if (existingDanhMucOpt.isEmpty()) {
            return null;
        }

        // Kiểm tra iddm mới
        String newIddm = danhMuc.getIddm();
        if (newIddm == null || newIddm.isEmpty()) {
            throw new IllegalArgumentException("ID danh mục mới không được để trống");
        }

        // Nếu iddm mới khác iddm cũ và iddm mới đã tồn tại, báo lỗi
        if (!newIddm.equals(iddm) && danhMucRepository.existsById(newIddm)) {
            throw new IllegalArgumentException("ID danh mục mới đã tồn tại: " + newIddm);
        }

        // Xóa bản ghi cũ
        danhMucRepository.deleteById(iddm);

        // Tạo bản ghi mới với iddm mới
        DanhMuc newDanhMuc = new DanhMuc();
        newDanhMuc.setIddm(newIddm);
        newDanhMuc.setTen(danhMuc.getTen());

        return danhMucRepository.save(newDanhMuc);
    }

    public void deleteDanhMuc(String iddm) {
        if (!danhMucRepository.existsById(iddm)) {
            throw new IllegalArgumentException("Danh mục không tồn tại: " + iddm);
        }
        danhMucRepository.deleteById(iddm);
    }
}