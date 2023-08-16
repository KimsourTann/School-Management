package com.example.SchoolPayment.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SchoolPayment.model.ClassModel;
import com.example.SchoolPayment.model.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel, Integer> {
      @Transactional
      @Modifying
      @Query(value = "update course_info set pay = ?2 where id_course = ?1", nativeQuery = true)
      int updatePay(int idCourse, int pay);

      @Query(value = "SELECT * FROM course_info where id_class = ?1 and id_stu = ?2", nativeQuery = true)
      public CourseModel findCourse(int id_class, int id_stu);

}
