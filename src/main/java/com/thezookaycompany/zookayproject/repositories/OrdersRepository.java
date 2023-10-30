package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import jakarta.persistence.criteria.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders ,Integer> {
     Orders findOrdersByOrderID (Integer orderID);
     @Query("SELECT o FROM Orders o WHERE LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
     List<Orders> findOrdersByDescriptionContainingKeyword(@Param("keyword") String keyword);
     @Query("SELECT o FROM Orders o ORDER BY o.orderID ASC")
     List<Orders> findAllByOrderIDAsc();
     @Query("SELECT o FROM Orders o ORDER BY o.orderID DESC")
     List<Orders> findAllByOrderIDDesc();

}
