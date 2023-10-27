package com.novianto.challage4.controller;

import com.novianto.challage4.dto.MerchantDto;
import com.novianto.challage4.entity.Merchant;
import com.novianto.challage4.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map<String, Object>> saveMerchant(@RequestBody MerchantDto request) {
        return new ResponseEntity<Map<String, Object>>(merchantService.saveMerchant(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update/{idMerchant}", "/update/{idMerchant}/"})
    public ResponseEntity<Map<String, Object>> updateMerchant(@RequestBody MerchantDto request, @PathVariable("idMerchant") UUID idMerchant) {
        return new ResponseEntity<Map<String, Object>>(merchantService.updateMerchant(idMerchant, request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{idMerchant}", "/delete/{idMerchant}/"})
    public ResponseEntity<Map<String, Object>> deleteMerchant(@PathVariable("idMerchant") UUID idMerchant) {
        return new ResponseEntity<Map<String, Object>>(merchantService.deleteMerchant(idMerchant), HttpStatus.OK);
    }

    @GetMapping(value = {"/get/{idMerchant}", "/get/{idMerchant}/"})
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("idMerchant") UUID idMerchant) {
        return new ResponseEntity<Map<String, Object>>(merchantService.getMerchantById(idMerchant), HttpStatus.OK);
    }

    @GetMapping(value = {"/allMerchant", "/allMerchant/"})
    public ResponseEntity<List<Merchant>> findAllMerchant(){
        return new ResponseEntity<>(merchantService.getAllMerchant(), HttpStatus.OK);
    }
}
