package com.example.pet.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "giohang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idgio;

    @ManyToOne
    @JoinColumn(name = "id_nd")
    private NguoiDung nguoidung;

    @ManyToOne
    @JoinColumn(name = "idsp")
    private SanPham sanpham;

    private int soluong;
}
