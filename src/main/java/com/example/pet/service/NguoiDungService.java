package com.example.pet.service;

import com.example.pet.models.NguoiDung;
import com.example.pet.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<NguoiDung> getAllNguoiDungs() {
        return nguoiDungRepository.findAll();
    }

    public Optional<NguoiDung> getNguoiDungById(int id) {
        return nguoiDungRepository.findById(id);
    }

    public Optional<NguoiDung> getNguoiDungByEmail(String email) {
        return nguoiDungRepository.findByEmail(email);
    }

    public NguoiDung updateNguoiDung(int id, NguoiDung nguoiDung) {
        Optional<NguoiDung> existingUser = nguoiDungRepository.findById(id);
        if (existingUser.isPresent()) {
            NguoiDung updatedUser = existingUser.get();
            updatedUser.setTen(nguoiDung.getTen());
            updatedUser.setSdt(nguoiDung.getSdt());
            updatedUser.setEmail(nguoiDung.getEmail());
            updatedUser.setDiachi(nguoiDung.getDiachi());
            updatedUser.setLoaind(nguoiDung.getLoaind());
            if (nguoiDung.getMatkhau() != null && !nguoiDung.getMatkhau().isEmpty()) {
                updatedUser.setMatkhau(passwordEncoder.encode(nguoiDung.getMatkhau()));
            }
            return nguoiDungRepository.save(updatedUser);
        }
        return null;
    }

    public String addNguoiDung(NguoiDung nguoiDung) {
        Optional<NguoiDung> existingUser = nguoiDungRepository.findByEmail(nguoiDung.getEmail());
        if (existingUser.isPresent()) {
            return "Email đã tồn tại!";
        }
        nguoiDung.setMatkhau(passwordEncoder.encode(nguoiDung.getMatkhau()));
        nguoiDungRepository.save(nguoiDung);
        return "Đăng ký thành công!";
    }

    public NguoiDung updateUserByEmail(String email, String hoten, String sdt, String diaChi) {
        return nguoiDungRepository.findByEmail(email)
                .map(nguoiDung -> {
                    nguoiDung.setTen(hoten);
                    nguoiDung.setSdt(sdt);
                    nguoiDung.setDiachi(diaChi);
                    return nguoiDungRepository.save(nguoiDung);
                })
                .orElse(null);
    }

    public boolean updatePassword(String email, String newPassword) {
        Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findByEmail(email);
        if (optionalNguoiDung.isPresent()) {
            NguoiDung nguoiDung = optionalNguoiDung.get();
            nguoiDung.setMatkhau(passwordEncoder.encode(newPassword));
            nguoiDungRepository.save(nguoiDung);
            return true;
        }
        return false;
    }

    public void deleteNguoiDung(int id) {
        nguoiDungRepository.deleteById(id);
    }
}