package com.example.SchoolPayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.SchoolPayment.model.UserInfoModel;

public interface UserRepository extends JpaRepository<UserInfoModel, Integer> {
      public UserInfoModel findTop1ByName(String name);


}