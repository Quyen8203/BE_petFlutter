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

    public Optional<DanhMuc> getDanhMucById(String id) {
        return danhMucRepository.findById(id);
    }

    public DanhMuc addDanhMuc(DanhMuc danhMuc) {
        return danhMucRepository.save(danhMuc);
    }

    public void deleteDanhMuc(String id) {
        danhMucRepository.deleteById(id);
    }
}
