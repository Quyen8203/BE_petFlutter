package com.example.pet.service;

import com.example.pet.models.Loai;
import com.example.pet.repository.LoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiService {
    @Autowired
    private LoaiRepository loaiRepository;

    public List<Loai> getAllLoai() {
        return loaiRepository.findAll();
    }

    public Optional<Loai> getLoaiById(int id) {
        return loaiRepository.findById(id);
    }

    public Loai addLoai(Loai loai) {
        return loaiRepository.save(loai);
    }

    public Loai updateLoai(int id, Loai loaiDetails) {
        return loaiRepository.findById(id)
                .map(loai -> {
                    loai.setHuongvi(loaiDetails.getHuongvi());
                    loai.setSokg(loaiDetails.getSokg());
                    loai.setGia(loaiDetails.getGia());
                    return loaiRepository.save(loai);
                })
                .orElse(null);
    }

    public void deleteLoai(int id) {
        loaiRepository.deleteById(id);
    }
}
