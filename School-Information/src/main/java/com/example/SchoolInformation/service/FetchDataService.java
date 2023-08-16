package com.example.SchoolInformation.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.SchoolInformation.model.ClassModel;
import com.example.SchoolInformation.model.UserInfoModel;
import com.example.SchoolInformation.model.UserRequest;
import com.example.SchoolInformation.repository.ClassRepository;
import com.example.SchoolInformation.repository.CourseRepository;
import com.example.SchoolInformation.repository.UserRepository;
import com.google.gson.Gson;

@Service
public class FetchDataService {

      @Value("${redis-key}")
      String REDISKEY;

      @Autowired
      UserRepository userRepository;

      @Autowired
      RedisTemplate<String, String> redis;

      @Autowired
      ClassRepository classRepository;

      @Autowired
      CourseRepository courseRepository;

      public String getAllUser() {
            return userRepository.findAll().toString();
      }

      public UserInfoModel getUserByName(String name) {
            var user = redis.opsForHash().get(REDISKEY, name);
            if (user == null) {
                  UserInfoModel model = userRepository.findTop1ByName(name);
                  if (model == null) {
                        return null;
                  }

                  if (redis.getExpire(REDISKEY) < 0) {

                        redis.expire(REDISKEY, 30, TimeUnit.MINUTES);

                  }
                  user = model.toJson();
                  redis.opsForHash().put(REDISKEY, name, model.toJson());
            }
            return new Gson().fromJson(user.toString(), UserInfoModel.class);
      }

      public ClassModel getClassByClass_Name(String className) {
            var classModel = redis.opsForHash().get(REDISKEY, className);
            if (classModel == null) {
                  ClassModel model = classRepository.findTop1ByClassname(className);
                  if (model == null) {
                        return null;
                  }
                  if (redis.getExpire(REDISKEY) < 0) {
                        redis.expire(REDISKEY, 30, TimeUnit.MINUTES);

                  }
                  classModel = model.toJson();
                  redis.opsForHash().put(REDISKEY, className, model.toJson());
            }
            return new Gson().fromJson(classModel.toString(), ClassModel.class);

      }

      public void saveUser(UserInfoModel userModel) {

            userRepository.save(userModel);
      }

      public void saveClass(ClassModel classModel) {
            System.out.println(classModel.toJson());
            classRepository.save(classModel);
      }

      public void deleteUser(UserInfoModel userModel) {
            redis.delete(REDISKEY);
            if (userModel.getType().equalsIgnoreCase("TEACHER")) {

                  courseRepository.deleteTeacher(userModel.getId());
            } else {
                  courseRepository.deleteStudent(userModel.getId());

            }
            userRepository.delete(userModel);
      }

      public void deleteClass(ClassModel classModel) {
            redis.delete(REDISKEY);
            courseRepository.deleteClass(classModel.getId_class());
            classRepository.delete(classModel);

      }

      public void updateClass(ClassModel classModel, String operation) {
            redis.delete(REDISKEY);

      }

      public int updateUser(String name, String newData, String operation) {
            redis.delete(REDISKEY);
            if (operation.equalsIgnoreCase("Name")) {
                  return userRepository.updateName(name, newData);
            } else if (operation.equalsIgnoreCase("address")) {

                  return userRepository.updateAddress(name, newData);
            } else if (operation.equalsIgnoreCase("phone_number")) {

                  return userRepository.updatePhoneNumber(name, newData);
            }

            return 0;

      }

      public int updateClass(String name, String newData, String operation) {
            redis.delete(REDISKEY);
            if (operation.equalsIgnoreCase("ClassName")) {
                  return classRepository.updateName(name, newData);
            } else if (operation.equalsIgnoreCase("Duration")) {

                  return classRepository.updateDuration(name, Integer.parseInt(newData));
            } else if (operation.equalsIgnoreCase("Price")) {
                  System.out.println(newData);
                  return classRepository.updatePrice(name, Float.parseFloat(newData));
            }

            return 0;

      }

}