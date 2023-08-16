package com.example.SchoolInformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.SchoolInformation.model.ClassModel;

public interface ClassRepository extends JpaRepository<ClassModel, Integer> {
      public ClassModel findTop1ByClassname(String class_name);

      @Transactional
      @Modifying
      @Query(value = "update class_info set class_name = ?2 where class_name = ?1", nativeQuery = true)
      int updateName(String name, String newName);

      @Transactional
      @Modifying
      @Query(value = "update class_info set duration = ?2 where class_name = ?1", nativeQuery = true)
      int updateDuration(String name, int newDuration);

      @Transactional
      @Modifying
      @Query(value = "update class_info set price = ?2 where class_name = ?1", nativeQuery = true)
      int updatePrice(String name, float newPrice);
}
