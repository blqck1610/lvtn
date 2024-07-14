package com.lvtn.user.repository;

import com.lvtn.user.dto.AddressDto;
import com.lvtn.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer > {
    List<Address> findByUserId(Integer id);
}
