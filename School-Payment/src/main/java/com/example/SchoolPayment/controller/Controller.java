package com.example.SchoolPayment.controller;

import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SchoolPayment.model.ClassModel;
import com.example.SchoolPayment.model.CourseModel;
import com.example.SchoolPayment.model.GetCourseRequest;
import com.example.SchoolPayment.model.PayRequest;
import com.example.SchoolPayment.model.Response;
import com.example.SchoolPayment.model.UserInfoModel;
import com.example.SchoolPayment.service.SchoolPaymentService;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;

@RestController
@RequestMapping("/SchoolManagemnt/v1")
public class Controller {

      @Autowired
      SchoolPaymentService service;

      @PostMapping(value = "/getCourse", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getCourse(@RequestBody GetCourseRequest request) {
            String correlationId = UUID.randomUUID().toString();
            String studentName = request.getStudent_name();
            String className = request.getClass_name();
            String teacherName = request.getTeacher_name();
            ClassModel classModel = service.getClassByClass_Name(className);
            UserInfoModel teacher = service.getUserByName(teacherName);
            UserInfoModel student = service.getUserByName(studentName);

            if (classModel == null || teacher == null || student == null) {
                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "Bad Request", null,
                                          "Invalid Input")));
            }
            service.saveCourse(new CourseModel(0, classModel.getId_class(), student.getId(), teacher.getId(), 0));

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", null,
                                    "Successfully"));

      }

      @PostMapping(value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getCourse(@RequestBody PayRequest request) {
            String correlationId = UUID.randomUUID().toString();
            String studentName = request.getStudent_name();
            String className = request.getClass_name();

            ClassModel classModel = service.getClassByClass_Name(className);
            UserInfoModel student = service.getUserByName(studentName);

            if (classModel == null || student == null) {
                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "Bad Request", null,
                                          "Invalid Input")));
            }
            service.updatePay(classModel.getId_class(), student.getId(), 1);

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", null,
                                    "Successfully"));

      }

      @GetMapping(value = "/student/{studentName}/class/{className}:getPayStatus", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getUserInfo(@PathVariable("className") String className,
                  @PathVariable("studentName") String studentName) {
            String correlationId = UUID.randomUUID().toString();
            // get User Information
            UserInfoModel userModel = service.getUserByName(studentName);
            ClassModel classModel = service.getClassByClass_Name(className);

            // Check If user exists or Not
            if (userModel == null || classModel == null) {

                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "Bad Request", null,
                                          "Invalid Input")));
            }
            CourseModel model = service.findCourse(classModel.getId_class(), userModel.getId());
            // Convert UserInfoModel to Json object
            JSONObject result = new JSONObject();
            if (model.getPay() == 1) {
                  result.put("Pay", true);
            } else {
                  result.put("Pay", false);
            }

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Sccuess", result.toMap(),
                                    "Successfully"));

      }

}
