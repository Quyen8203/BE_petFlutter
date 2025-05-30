package com.example.pet.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vanchuyen")
public class VanChuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvc")
    private int id;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_status_update")
    private Date lastStatusUpdate;

    @OneToOne
    @JoinColumn(name = "iddon", nullable = false)
    @JsonIgnore
    private DonHang donHang;
}