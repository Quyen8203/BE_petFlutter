package com.example.pet.service;

import com.example.pet.models.ChiTieu;
import com.example.pet.models.DonHang;
import com.example.pet.repository.ChiTieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTieuService {

    @Autowired
    private ChiTieuRepository repository;

    public List<ChiTieu> getAll() {
        return repository.findAll();
    }

    public List<ChiTieu> getByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public void saveFromDonHang(DonHang dh) {
        ChiTieu ct = new ChiTieu();
        ct.setTenhd(dh.getTensp());
        ct.setSoluong(dh.getSoluong());
        ct.setThanhtien(dh.getThanhtien());
        ct.setIddon(dh.getId());
        ct.setUserId(dh.getNguoidung().getId());
        ct.setIdl(dh.getNguoidung().getId());
        repository.save(ct);
    }


    public void delete(int idchitieu) {
        repository.deleteById(idchitieu);
    }
}
