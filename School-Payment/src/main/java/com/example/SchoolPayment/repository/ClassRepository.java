package com.example.SchoolPayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.SchoolPayment.model.ClassModel;

public interface ClassRepository extends JpaRepository<ClassModel, Integer> {
      public ClassModel findTop1ByClassname(String class_name);


}
