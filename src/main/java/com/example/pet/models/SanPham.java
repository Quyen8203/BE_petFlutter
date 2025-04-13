package com.example.pet.models;
import jakarta.persistence.*;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import com.example.pet.models.DanhMuc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sanpham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsp;
    @Column(nullable = false)
    private String ten; // Tên sản phẩm

    @Column(nullable = false)
    private double dongia; // Giá sản phẩm

    private String mota; // Mô tả (có thể null)

    @Column(nullable = false)
    private int sl;

    private String hinhanh;

    @ManyToOne
    @JoinColumn(name = "iddm", nullable = false)
    private DanhMuc danhmuc;

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        return jsonConverter;
    }

}