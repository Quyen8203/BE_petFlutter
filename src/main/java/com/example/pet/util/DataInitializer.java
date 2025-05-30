package com.example.pet.util;

import com.example.pet.models.NguoiDung;
import com.example.pet.models.Role;
import com.example.pet.repository.NguoiDungRepository;
import com.example.pet.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

//        NguoiDung admin = new NguoiDung();
//        admin.setTen("Admin");
//        admin.setEmail("admin@gmail.com");
//        admin.setSdt("0123456789");
//        admin.setDiachi("123 Admin St");
//        admin.setMatkhau(passwordEncoder.encode("admin123"));
//        admin.setLoaind(0);
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        admin.setRoles(adminRoles);
//        nguoiDungRepository.save(admin);
//
//        NguoiDung user = new NguoiDung();
//        user.setTen("User");
//        user.setEmail("user@gmail.com");
//        user.setSdt("0987654321");
//        user.setDiachi("456 User St");
//        user.setMatkhau(passwordEncoder.encode("user123"));
//        user.setLoaind(1);
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRoles(userRoles);
//        nguoiDungRepository.save(user);
    }
}