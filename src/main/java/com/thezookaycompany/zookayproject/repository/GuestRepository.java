package com.thezookaycompany.zookayproject.repository;

import com.thezookaycompany.zookayproject.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

}
