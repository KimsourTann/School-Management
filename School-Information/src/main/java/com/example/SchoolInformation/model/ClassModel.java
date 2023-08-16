package com.example.SchoolInformation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "class_info")
public class ClassModel {
      @Id
      @Column(name = "id_class")
      int id_class;
      @Column(name = "class_name")
      String classname;
      @Column(name = "price")
      float price;
      @Column(name = "duration")
      int duration;

      public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
      }
}
