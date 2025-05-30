package com.example.pet.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data

@Table(name = "nguoidung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ten;

    @Column(nullable = false)
    private String sdt;

    @Column(unique = true, nullable = false)
    private String email;
    private String diachi;
    private String matkhau;

    @Column(name = "loaind", nullable = false)
    private int loaind;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public NguoiDung() {
    }

    public NguoiDung(int id, String ten, String sdt, String email, String diachi, String matkhau, int loaind, Set<Role> roles) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
        this.matkhau = matkhau;
        this.loaind = loaind;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getLoaind() {
        return loaind;
    }

    public void setLoaind(int loaind) {
        this.loaind = loaind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
