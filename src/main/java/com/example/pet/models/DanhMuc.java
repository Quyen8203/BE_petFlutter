package com.example.pet.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "danhmuc")
public class DanhMuc {
    @Id
    @Column(nullable = false)
    private String iddm;

    private String ten;
}