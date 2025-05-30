package com.example.pet.service;

import com.example.pet.dto.SanPhamDTO;
import com.example.pet.models.SanPham;
import com.example.pet.models.Loai;
import com.example.pet.repository.SanPhamRepository;
import com.example.pet.repository.LoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiRepository loaiRepository;

    @Transactional(readOnly = true)
    public List<SanPhamDTO> getAllSanPhams() {
        List<SanPham> sanPhams = sanPhamRepository.findAll();
        return sanPhams.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<SanPhamDTO> getSanPhamById(int idsp) {
        Optional<SanPham> sanPham = sanPhamRepository.findById(idsp);
        return sanPham.map(this::convertToDTO);
    }

    public SanPham addSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    public SanPham updateSanPham(int idsp, SanPham sanPham) {
        if (!sanPhamRepository.existsById(idsp)) {
            return null;
        }
        sanPham.setIdsp(idsp);
        return sanPhamRepository.save(sanPham);
    }

    public void deleteSanPham(int idsp) {
        sanPhamRepository.deleteById(idsp);
    }

    public SanPham addLoaiToSanPham(int sanPhamId, int loaiId) {
        Optional<SanPham> sanPhamOpt = sanPhamRepository.findById(sanPhamId);
        Optional<Loai> loaiOpt = loaiRepository.findById(loaiId);

        if (sanPhamOpt.isPresent() && loaiOpt.isPresent()) {
            SanPham sanPham = sanPhamOpt.get();
            Loai loai = loaiOpt.get();
            sanPham.getLoais().add(loai);
            return sanPhamRepository.save(sanPham);
        }
        return null;
    }

    // Phương thức chuyển đổi từ SanPham sang SanPhamDTO
    private SanPhamDTO convertToDTO(SanPham sanPham) {
        // Sao chép loais sang một List mới để tránh ConcurrentModificationException
        List<Loai> loaisCopy = new ArrayList<>(sanPham.getLoais());

        return new SanPhamDTO(
                sanPham.getIdsp(),
                sanPham.getTen(),
                sanPham.getDongia(),
                sanPham.getMota(),
                sanPham.getSl(),
                sanPham.getHinhanh(),
                sanPham.getDanhmuc(),
                sanPham.getLoais()
        );
    }
}