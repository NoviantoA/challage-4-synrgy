package com.novianto.challage4.repository;

import com.novianto.challage4.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID>, JpaSpecificationExecutor<Merchant> {

    @Query(value = "SELECT m FROM Merchant m WHERE m.id = :id")
    public Merchant getByIdMerchant(@Param("id") UUID id);
}
