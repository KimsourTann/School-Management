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
@Table(name = "user_info")
public class UserInfoModel {
      @Id
      @Column(name = "id")
      int id;
      @Column(name = "name")
      String name;
      @Column(name = "address")
      String address;
      @Column(name = "phone_number")
      String phone_number;

      @Column(name = "type")
      String type;

      public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
      }
}
