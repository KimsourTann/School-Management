package com.example.SchoolPayment.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.SchoolPayment.model.ClassModel;
import com.example.SchoolPayment.model.CourseModel;
import com.example.SchoolPayment.model.UserInfoModel;
import com.example.SchoolPayment.repository.ClassRepository;
import com.example.SchoolPayment.repository.CourseRepository;
import com.example.SchoolPayment.repository.UserRepository;
import com.google.gson.Gson;

@Service
public class SchoolPaymentService {
      @Value("${redis-key}")
      String REDISKEY;

      @Autowired
      UserRepository userRepository;
      @Autowired
      CourseRepository courseRepository;
      @Autowired
      RedisTemplate<String, String> redis;

      @Autowired
      ClassRepository classRepository;

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

      public void saveCourse(CourseModel courseModel) {

            courseRepository.save(courseModel);
      }

      public int updatePay(int idClass, int idStu, int pay) {
            redis.delete(REDISKEY);

            CourseModel model = courseRepository.findCourse(idClass, idStu);
            return courseRepository.updatePay(model.getId_course(), pay);

      }

      public CourseModel findCourse(int idClass, int idStu) {

            CourseModel model = courseRepository.findCourse(idClass, idStu);
            return model;

      }

}
