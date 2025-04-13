package com.example.pet.controller;


import com.example.pet.models.NguoiDung;
import com.example.pet.repository.NguoiDungRepository;

import com.example.pet.service.NguoiDungService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/nguoidung")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {})
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    // Lấy danh sách người dùng
    @GetMapping("/list")
    public List<NguoiDung> getAllNguoiDungs() {
        return nguoiDungService.getAllNguoiDungs();
    }

    // Tìm kiếm người dùng theo email
    @GetMapping("/email")
    public Optional<NguoiDung> getNguoiDungByEmail(@RequestParam String email) {
        return nguoiDungService.getNguoiDungByEmail(email);
    }

    // Lấy người dùng theo ID
    @GetMapping("/search")
    public Optional<NguoiDung> getNguoiDungById(@RequestParam int id) {
        return nguoiDungService.getNguoiDungById(id);
    }

    // Thêm người dùng mới
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody NguoiDung nguoiDung) {
        String result = nguoiDungService.addNguoiDung(nguoiDung);
        if (result.equals("Đăng ký thành công!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    // Cập nhật thông tin người dùng
    @PutMapping("/up")
    public NguoiDung updateNguoiDung(@RequestParam int id, @RequestBody NguoiDung nguoiDung) {
        return nguoiDungService.updateNguoiDung(id, nguoiDung);
    }

    // Cập nhật thông tin người dùng dựa trên email
    @PutMapping("/upmail")
    public NguoiDung updateUserByEmail(@RequestParam String email,
                                       @RequestBody NguoiDung updatedUser) {
        return nguoiDungService.updateUserByEmail(email, updatedUser.getTen(), updatedUser.getSdt(), updatedUser.getDiachi());
    }

    @PutMapping("/upPass")
    public ResponseEntity<?> updatePassword(@RequestParam String email, @RequestBody Map<String, String> body) {
        System.out.println("Dữ liệu nhận được: " + body);

        // Kiểm tra nếu body không có key "newPass"
        if (!body.containsKey("newPass")) {
            return ResponseEntity.badRequest().body("Thiếu trường newPass");
        }

        String newPass = body.get("newPass");

        // Cập nhật mật khẩu vào database
        nguoiDungRepository.findByEmail(email).ifPresent(user -> {
            user.setMatkhau(newPass);
            nguoiDungRepository.save(user);
        });

        return ResponseEntity.ok("Mật khẩu đã được cập nhật");
    }





    // Xóa người dùng theo ID
    @DeleteMapping("/del")
    public void deleteNguoiDung(@RequestParam int id) {
        nguoiDungService.deleteNguoiDung(id);
    }
}
