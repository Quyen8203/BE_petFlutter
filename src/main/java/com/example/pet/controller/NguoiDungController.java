package com.example.pet.controller;

import com.example.pet.models.NguoiDung;
import com.example.pet.repository.NguoiDungRepository;
import com.example.pet.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/nguoidung")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public List<NguoiDung> getAllNguoiDungs() {
        return nguoiDungService.getAllNguoiDungs();
    }

    @GetMapping("/email")
    public Optional<NguoiDung> getNguoiDungByEmail(@RequestParam String email) {
        return nguoiDungService.getNguoiDungByEmail(email);
    }

    @GetMapping("/search")
    public Optional<NguoiDung> getNguoiDungById(@RequestParam int id) {
        return nguoiDungService.getNguoiDungById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody NguoiDung nguoiDung) {
        String result = nguoiDungService.addNguoiDung(nguoiDung);
        if (result.equals("Đăng ký thành công!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("/up")
    public NguoiDung updateNguoiDung(@RequestParam int id, @RequestBody NguoiDung nguoiDung) {
        return nguoiDungService.updateNguoiDung(id, nguoiDung);
    }

    @PutMapping("/upmail")
    public NguoiDung updateUserByEmail(@RequestParam String email,
                                       @RequestBody NguoiDung updatedUser) {
        return nguoiDungService.updateUserByEmail(email, updatedUser.getTen(), updatedUser.getSdt(), updatedUser.getDiachi());
    }

    @PutMapping("/upPass")
    public ResponseEntity<?> updatePassword(@RequestParam String email, @RequestBody Map<String, String> body) {
        if (!body.containsKey("newPass")) {
            return ResponseEntity.badRequest().body("Thiếu trường newPass");
        }

        String newPass = body.get("newPass");
        boolean updated = nguoiDungService.updatePassword(email, newPass);
        if (updated) {
            return ResponseEntity.ok("Mật khẩu đã được cập nhật");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng với email: " + email);
        }
    }

    @DeleteMapping("/del")
    public void deleteNguoiDung(@RequestParam int id) {
        nguoiDungService.deleteNguoiDung(id);
    }

    @PutMapping("/updateRole")
    public ResponseEntity<NguoiDung> updateUserRole(@RequestParam int id, @RequestBody Map<String, Integer> body) {
        if (!body.containsKey("loaind")) {
            return ResponseEntity.badRequest().body(null);
        }
        int loaind = body.get("loaind");
        if (loaind != 0 && loaind != 1) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<NguoiDung> optionalNguoiDung = nguoiDungService.getNguoiDungById(id);
        if (optionalNguoiDung.isPresent()) {
            NguoiDung nguoiDung = optionalNguoiDung.get();
            nguoiDung.setLoaind(loaind);
            nguoiDungRepository.save(nguoiDung);
            return ResponseEntity.ok(nguoiDung);
        }
        return ResponseEntity.notFound().build();
    }
}