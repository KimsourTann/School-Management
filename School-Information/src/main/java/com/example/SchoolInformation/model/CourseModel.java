package com.example.SchoolInformation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "course_info")
public class CourseModel {
      @Id
      @Column(name = "id_course")
      int id_course;
      @Column(name = "id_class")
      int id_class;
      @Column(name = "id_stu")
      int id_stu;
      @Column(name = "id_teach")
      int id_teach;
      @Column(name = "pay")
      int pay;
}
