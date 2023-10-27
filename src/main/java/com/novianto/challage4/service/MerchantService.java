package com.novianto.challage4.service;

import com.novianto.challage4.dto.MerchantDto;
import com.novianto.challage4.entity.Merchant;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MerchantService {

    List<Merchant> getAllMerchant();

    Map<String, Object> saveMerchant(MerchantDto merchantDto);

    Map<String, Object> updateMerchant(UUID idMerchant, MerchantDto merchantDto);

    Map<String, Object> deleteMerchant(UUID idMerchant);

    Map<String, Object> getMerchantById(UUID idMerchant);
}
