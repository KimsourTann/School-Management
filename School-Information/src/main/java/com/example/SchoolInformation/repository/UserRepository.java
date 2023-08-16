package com.example.SchoolInformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.SchoolInformation.model.UserInfoModel;

public interface UserRepository extends JpaRepository<UserInfoModel, Integer> {
      public UserInfoModel findTop1ByName(String name);

      @Transactional
      @Modifying
      @Query(value = "update user_info set name = ?2 where name = ?1", nativeQuery = true)
      int updateName(String name, String newName);

      @Transactional
      @Modifying
      @Query(value = "update user_info set address = ?2 where name = ?1", nativeQuery = true)
      int updateAddress(String name, String newAddress);

      @Transactional
      @Modifying
      @Query(value = "update user_info set phone_number = ?2 where name = ?1", nativeQuery = true)
      int updatePhoneNumber(String name, String newPhoneNumber);

}