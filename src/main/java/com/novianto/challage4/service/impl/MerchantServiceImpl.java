package com.novianto.challage4.service.impl;

import com.novianto.challage4.dto.MerchantDto;
import com.novianto.challage4.entity.Merchant;
import com.novianto.challage4.repository.MerchantRepository;
import com.novianto.challage4.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public List<Merchant> getAllMerchant() {
        return merchantRepository.findAll();
    }

    @Override
    public Map<String, Object> saveMerchant(MerchantDto merchantDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (merchantDto != null) {
                Merchant merchant = new Merchant();
                merchant.setId(UUID.randomUUID());
                merchant.setMerchantName(merchantDto.getMerchantName());
                merchant.setMerchantLocation(merchantDto.getMerchantLocation());
                merchant.setOpen(true);

                Optional<Merchant> optionalMerchant = Optional.of(merchantRepository.save(merchant));

                if (optionalMerchant.isPresent()) {
                    response.put("success", true);
                    response.put("message", "Merchant berhasil disimpan");
                    response.put("merchant", optionalMerchant.get());
                } else {
                    response.put("success", false);
                    response.put("message", "Gagal menyimpan merchant");
                }
            } else {
                response.put("success", false);
                response.put("message", "Data Merchant tidak valid");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menyimpan merchant");
        }
        return response;
    }

    @Override
    public Map<String, Object> updateMerchant(UUID idMerchant, MerchantDto merchantDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Merchant> existingMerchant = Optional.ofNullable(merchantRepository.getByIdMerchant(idMerchant));

            if (existingMerchant.isPresent()) {
                Merchant newMerchant = existingMerchant.get();
                newMerchant.setMerchantName(merchantDto.getMerchantName());
                newMerchant.setMerchantLocation(merchantDto.getMerchantLocation());
                newMerchant.setOpen(merchantDto.isOpen());

                Merchant updatedMerchant = merchantRepository.save(newMerchant);

                response.put("success", true);
                response.put("message", "Merchant berhasil diperbarui");
                response.put("merchant", updatedMerchant);
            } else {
                response.put("success", false);
                response.put("message", "Merchant tidak ditemukan");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Gagal memperbarui merchant");
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteMerchant(UUID idMerchant) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Merchant> findMerchantOptional = Optional.ofNullable(merchantRepository.getByIdMerchant(idMerchant));

            if (findMerchantOptional.isPresent()) {
                Merchant merchant = findMerchantOptional.get();
                merchantRepository.delete(merchant);
                response.put("success", true);
                response.put("message", "Data merchant ditemukan dan dihapus");
                response.put("data", merchant);
            } else {
                response.put("success", false);
                response.put("message", "Data merchant tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menghapus merchant");
        }
        return response;
    }

    @Override
    public Map<String, Object> getMerchantById(UUID idMerchant) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Merchant> findMerchantOptional = Optional.ofNullable(merchantRepository.getByIdMerchant(idMerchant));

            if (findMerchantOptional.isPresent()) {
                Merchant merchant = findMerchantOptional.get();
                response.put("success", true);
                response.put("message", "Data merchant ditemukan");
                response.put("data", merchant);
            } else {
                response.put("success", false);
                response.put("message", "Data merchant tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal mengambil data merchant");
        }
        return response;
    }
}
