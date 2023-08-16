package com.example.SchoolInformation.controller;

import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SchoolInformation.model.ClassModel;
import com.example.SchoolInformation.model.ClassRequest;
import com.example.SchoolInformation.model.UserRequest;
import com.example.SchoolInformation.model.Response;
import com.example.SchoolInformation.model.UserInfoModel;
import com.example.SchoolInformation.service.FetchDataService;

@RestController
@RequestMapping("/SchoolManagemnt/v1")
class controller {

      @Autowired
      FetchDataService service;

      @GetMapping(value = "/{name}:getUserInfo", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getUserInfo(@PathVariable("name") String name) {
            String correlationId = UUID.randomUUID().toString();
            // get User Information
            UserInfoModel userModel = service.getUserByName(name);

            // Check If user exists or Not
            if (userModel == null) {

                  return ResponseEntity.status(HttpStatus.valueOf(404))
                              .body((new Response(correlationId, new Date(), "0001", "Not Found", null,
                                          "Not Found User Information")));
            }

            // Convert UserInfoModel to Json object
            JSONObject result = new JSONObject(userModel);

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Found", result.toMap(),
                                    "Found User Information"));

      }

      @GetMapping(value = "/{class_name}:getClassInfo", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getClassInfo(@PathVariable("class_name") String name) {
            String correlationId = UUID.randomUUID().toString();
            // get Class Information
            ClassModel classModel = service.getClassByClass_Name(name);

            // Check If class exist or not
            if (classModel == null) {

                  return ResponseEntity.status(HttpStatus.valueOf(404))
                              .body((new Response(correlationId, new Date(), "0001", "Not Found", null,
                                          "Not Found Class Information")));
            }

            // Convert ClassModel to Json object
            JSONObject result = new JSONObject(classModel);

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Found", result.toMap(),
                                    "Found Class Information"));

      }

      @PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
            String correlationId = UUID.randomUUID().toString();
            // Read Information from Request Body
            String name = request.getName();
            String address = request.getAddress();
            String phoneNumber = request.getPhone_number();
            String type = request.getType();

            // Check If user exists or not
            UserInfoModel userModel = service.getUserByName(name);
            if (userModel != null) {

                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "User exists", null,
                                          "User already exists")));
            }

            // Insert User to Database
            service.saveUser(new UserInfoModel(0, name, address, phoneNumber, type));
            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", request.toString(),
                                    "Successfully Create User"));
      }

      @PostMapping(value = "/createClass", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> createClass(@RequestBody ClassRequest request) {
            String correlationId = UUID.randomUUID().toString();
            // Read information from request body
            String name = request.getClass_name();
            int duration = request.getDuration();
            float price = request.getPrice();
            // Check it Class exist or not
            ClassModel classModel = service.getClassByClass_Name(name);
            if (classModel != null) {

                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "Class exists", null,
                                          "Class already exists")));
            }
            // Insert Class to Database
            service.saveClass(new ClassModel(0, name, price, duration));
            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", request.toString(),
                                    "Successfully Create Class"));

      }

      @PostMapping(value = "/deleteUser", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> deleteUser(@RequestBody UserRequest request) {
            String correlationId = UUID.randomUUID().toString();
            // Read Information from Request Body
            String name = request.getName();

            // Check If user exists or not
            UserInfoModel userModel = service.getUserByName(name);
            if (userModel == null) {

                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "User doesn't exists", null,
                                          "User doesn't exists")));
            }
            // Delete User from Database
            service.deleteUser(userModel);

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", null,
                                    "Successfully Delete User"));

      }

      @PostMapping(value = "/deleteClass", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> deleteClass(@RequestBody ClassRequest request) {
            String correlationId = UUID.randomUUID().toString();
            // Read Information from Request Body
            String name = request.getClass_name();

            // Check If user exists or not
            ClassModel classModel = service.getClassByClass_Name(name);
            if (classModel == null) {

                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "Class doesn't exists", null,
                                          "Class doesn't exists")));
            }
            // Delete User from Database
            service.deleteClass(classModel);

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", null,
                                    "Successfully Delete Class"));

      }

      @PostMapping(value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> updateUser(@RequestParam(name = "name") String name,
                  @RequestParam(name = "new") String newData, @RequestParam(name = "operation") String operation) {
            String correlationId = UUID.randomUUID().toString();

            int result = service.updateUser(name, newData, operation);
            if (result == 0) {

                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "Bad Request", null,
                                          "Bad Request")));
            }

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", null,
                                    "Successfully Update User"));

      }

      @PostMapping(value = "/updateClass", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> updateClass(@RequestParam(name = "name") String name,
                  @RequestParam(name = "new") String newData, @RequestParam(name = "operation") String operation) {
            String correlationId = UUID.randomUUID().toString();

            int result = service.updateClass(name, newData, operation);
            if (result == 0) {

                  return ResponseEntity.status(HttpStatus.valueOf(400))
                              .body((new Response(correlationId, new Date(), "0001", "Bad Request", null,
                                          "Bad Request")));
            }

            return ResponseEntity.status(HttpStatus.valueOf(200))
                        .body(new Response(correlationId, new Date(), "0000", "Success", null,
                                    "Successfully Update Class"));

      }

}
