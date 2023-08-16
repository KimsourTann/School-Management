package com.example.SchoolInformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.SchoolInformation.model.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel, Integer> {
      @Transactional
      @Modifying
      @Query(value = "delete from course_info where id_stu = ?1 ", nativeQuery = true)
      int deleteStudent(int id);

      @Transactional
      @Modifying
      @Query(value = "delete from course_info where id_teach = ?1 ", nativeQuery = true)
      int deleteTeacher(int id);

      @Transactional
      @Modifying
      @Query(value = "delete from course_info where id_class = ?1 ", nativeQuery = true)
      int deleteClass(int id);
}
