package com.novianto.challage4.repository;

import com.novianto.challage4.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {

    @Query(value = "SELECT o FROM Order o WHERE o.id = :id")
    public Order getByIdOrder(@Param("id") UUID id);
}
