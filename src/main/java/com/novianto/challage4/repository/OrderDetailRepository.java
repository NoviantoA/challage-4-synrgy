package com.novianto.challage4.repository;

import com.novianto.challage4.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID>, JpaSpecificationExecutor<OrderDetail> {

    @Query(value = "SELECT od FROM OrderDetail od WHERE od.id = :id")
    public OrderDetail getByIdOrderDetail(@Param("id") UUID id);

    @Query(value = "SELECT od FROM order_details od WHERE id = :id", nativeQuery = true)
    public Object getByIdNative(@Param("id") UUID id);

    @Query(value = "select od from OrderDetail od where LOWER(od.totalPrice) like LOWER(:nameParam)")
    public Page<OrderDetail> getByLikeTotalPrice(@Param("nameParam") Double nameParam, Pageable pageable);

    @Query(value = "select od from OrderDetail od ")
    public Page<OrderDetail> getALlPage(Pageable pageable);

    Page<OrderDetail> findByQuantityAndTotalPrice(Integer quantity, Double totalPrice, Pageable pageable);

    @Query("select count(od) from OrderDetail od where od.totalPrice = ?1")
    long countByTotalPrice(Double totalPrice);

    long count();

    @Query("select sum(od.id) from OrderDetail od")
    long sumProduct();
}
