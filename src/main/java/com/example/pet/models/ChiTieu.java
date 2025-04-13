package com.example.pet.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chitieu", schema = "pet_flutter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idchitieu;

    private String tenhd;
    private int soluong;
    private double thanhtien;

    @Column(name = "iddon")
    private int iddon;


    @Column(name = "id_nd")
    private int userId;

}
