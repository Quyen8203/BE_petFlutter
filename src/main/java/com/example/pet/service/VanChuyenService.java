package com.example.pet.service;

import com.example.pet.models.VanChuyen;
import com.example.pet.models.DonHang;
import com.example.pet.repository.VanChuyenRepository;
import com.example.pet.repository.DonHangRepository;
import com.example.pet.repository.SanPhamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class VanChuyenService {

    private static final Logger logger = LoggerFactory.getLogger(VanChuyenService.class);

    @Autowired
    private VanChuyenRepository vanChuyenRepository;

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private ChiTieuService chiTieuService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Value("${transport.status.durations:15,30,15}")
    private String[] durationStrings;

    private long[] STATUS_DURATIONS;
    private static final String[] STATUSES = {
            "Xác nhận đặt",
            "Đang vận chuyển",
            "Đang giao hàng",
            "Thành công"
    };

    @PostConstruct
    public void init() {
        STATUS_DURATIONS = Arrays.stream(durationStrings)
                .mapToLong(d -> Long.parseLong(d) * 60 * 1000)
                .toArray();
    }

    public List<VanChuyen> getAllVanChuyen() {
        return vanChuyenRepository.findAll();
    }

    public List<VanChuyen> getVanChuyenByUserId(int userId) {
        return vanChuyenRepository.findByDonHang_Nguoidung_Id(userId);
    }

    @Transactional
    public VanChuyen updateVanChuyenStatus(int id, String newStatus) {
        VanChuyen vanChuyen = vanChuyenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vận chuyển với ID: " + id));

        if (!isValidStatus(newStatus)) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ: " + newStatus);
        }

//        int currentStatusIndex = Arrays.asList(STATUSES).indexOf(vanChuyen.getTrangThai());
//        int newStatusIndex = Arrays.asList(STATUSES).indexOf(newStatus);
//        if (newStatusIndex != currentStatusIndex + 1) {
//            throw new IllegalArgumentException("Không thể chuyển từ " + vanChuyen.getTrangThai() + " sang " + newStatus);
//        }

        vanChuyen.setTrangThai(newStatus);
        vanChuyen.setLastStatusUpdate(new Date());
        vanChuyenRepository.save(vanChuyen);

        if ("Thành công".equalsIgnoreCase(newStatus)) {
            processSuccessfulOrder(vanChuyen);
        }

        return vanChuyen;
    }

    public VanChuyen createVanChuyenForDonHang(DonHang donHang) {
        VanChuyen vanChuyen = new VanChuyen();
        vanChuyen.setTrangThai("Xác nhận đặt");
        vanChuyen.setLastStatusUpdate(new Date());
        vanChuyen.setDonHang(donHang);
        return vanChuyenRepository.save(vanChuyen);
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void updateTransportStatus() {
        List<VanChuyen> vanChuyens = vanChuyenRepository.findByTrangThaiNot("Thành công");
        for (VanChuyen vanChuyen : vanChuyens) {
            try {
                int currentStatusIndex = Arrays.asList(STATUSES).indexOf(vanChuyen.getTrangThai());
                if (currentStatusIndex >= 0 && currentStatusIndex < STATUSES.length - 1) {
                    long timeSinceLastUpdate = new Date().getTime() - vanChuyen.getLastStatusUpdate().getTime();
                    if (timeSinceLastUpdate >= STATUS_DURATIONS[currentStatusIndex]) {
                        vanChuyen.setTrangThai(STATUSES[currentStatusIndex + 1]);
                        vanChuyen.setLastStatusUpdate(new Date());
                        vanChuyenRepository.save(vanChuyen);

                        if ("Thành công".equalsIgnoreCase(vanChuyen.getTrangThai())) {
                            processSuccessfulOrder(vanChuyen);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Lỗi khi cập nhật vận chuyển ID {}: {}", vanChuyen.getId(), e.getMessage());
            }
        }
    }

    private void processSuccessfulOrder(VanChuyen vanChuyen) {
        chiTieuService.saveFromDonHang(vanChuyen.getDonHang());
        DonHang donHang = vanChuyen.getDonHang();
        sanPhamRepository.findById(donHang.getSanpham().getIdsp()).ifPresent(sanPham -> {
            if (sanPham.getSl() >= donHang.getSoluong()) {
                sanPham.setSl(sanPham.getSl() - donHang.getSoluong());
                sanPhamRepository.save(sanPham);
            } else {
                throw new IllegalArgumentException("Sản phẩm ID " + sanPham.getIdsp() + " không đủ số lượng trong kho!");
            }
        });
    }

    private boolean isValidStatus(String status) {
        return Arrays.stream(STATUSES).anyMatch(validStatus -> validStatus.equalsIgnoreCase(status));
    }
}