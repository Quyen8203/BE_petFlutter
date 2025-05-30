package com.example.pet.controller;

import com.example.pet.models.ChiTieu;
import com.example.pet.service.ChiTieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chi-tieu")
@CrossOrigin(origins = "", allowedHeaders = "*", methods = {})
public class ChiTieuController {

    @Autowired
    private ChiTieuService service;

    @GetMapping
    public List<ChiTieu> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<ChiTieu> getByUser(@PathVariable int userId) {
        return service.getByUserId(userId);
    }


}
