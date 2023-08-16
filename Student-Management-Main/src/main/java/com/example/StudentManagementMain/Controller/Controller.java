package com.example.StudentManagementMain.Controller;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.StudentManagementMain.Model.PayRequest;
import com.example.StudentManagementMain.Model.ClassRequest;
import com.example.StudentManagementMain.Model.UserRequest;
import com.example.StudentManagementMain.Model.GetCourseRequest;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/SchoolManagemnt/v1")
public class Controller {
      @Autowired
      private WebClient.Builder webClientBuilder;

      @Value("${school-information}")
      String infoUrl;

      @Value("${school-payment}")
      String paymentUrl;

      @GetMapping(value = "/{name}:getUserInfo", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getUserInfo(@PathVariable("name") String name) {
            String correlationId = UUID.randomUUID().toString();
            try {

                  String res = webClientBuilder.build().get()
                              .uri(infoUrl.concat(name) + ":getUserInfo")
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @GetMapping(value = "/{class_name}:getClassInfo", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getClassInfo(@PathVariable("class_name") String name) {
            String correlationId = UUID.randomUUID().toString();

            try {

                  String res = webClientBuilder.build().get()
                              .uri(infoUrl.concat(name) + ":getClassInfo")
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
            String correlationId = UUID.randomUUID().toString();

            try {
                  String res = webClientBuilder.build().post()
                              .uri(infoUrl.concat("createUser"))
                              .bodyValue(request)
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/createClass", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> createClass(@RequestBody ClassRequest request) {
            String correlationId = UUID.randomUUID().toString();
            try {
                  String res = webClientBuilder.build().post()
                              .uri(infoUrl.concat("createClass"))
                              .bodyValue(request)
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/deleteUser", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> deleteUser(@RequestBody UserRequest request) {
            String correlationId = UUID.randomUUID().toString();
            try {
                  String res = webClientBuilder.build().post()
                              .uri(infoUrl.concat("deleteUser"))
                              .bodyValue(request)
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/deleteClass", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> deleteClass(@RequestBody ClassRequest request) {
            try {
                  String res = webClientBuilder.build().post()
                              .uri(infoUrl.concat("deleteClass"))
                              .bodyValue(request)
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> updateUser(@RequestParam(name = "name") String name,
                  @RequestParam(name = "new") String newData, @RequestParam(name = "operation") String operation) {
            String correlationId = UUID.randomUUID().toString();
            try {
                  // Create a URI with base URL and path
                  String path = "updateUser";

                  // Create a UriComponentsBuilder and add parameters
                  UriComponentsBuilder builder = UriComponentsBuilder
                              .fromUriString(infoUrl)
                              .path(path)
                              .queryParam("name", name)
                              .queryParam("new", newData)
                              .queryParam("operation", operation);

                  // Build the final URI
                  String uri = builder.toUriString();
                  String res = webClientBuilder.build().post()
                              .uri(uri)

                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/updateClass", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> updateClass(@RequestParam(name = "name") String name,
                  @RequestParam(name = "new") String newData, @RequestParam(name = "operation") String operation) {

            try {
                  // Create a URI with base URL and path
                  String path = "updateClass";

                  // Create a UriComponentsBuilder and add parameters
                  UriComponentsBuilder builder = UriComponentsBuilder
                              .fromUriString(infoUrl)
                              .path(path)
                              .queryParam("name", name)
                              .queryParam("new", newData)
                              .queryParam("operation", operation);

                  // Build the final URI
                  String uri = builder.toUriString();
                  String res = webClientBuilder.build().post()
                              .uri(uri)
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/getCourse", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> getCourse(@RequestBody GetCourseRequest request) {
            String correlationId = UUID.randomUUID().toString();
            try {

                  String res = webClientBuilder.build().post()
                              .uri(paymentUrl.concat("getCourse"))
                              .bodyValue(request)
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

      @PostMapping(value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> pay(@RequestBody PayRequest request) {
            String correlationId = UUID.randomUUID().toString();
            try {

                  String res = webClientBuilder.build().post()
                              .uri(paymentUrl.concat("pay"))
                              .bodyValue(request)
                              .exchangeToMono(response -> {
                                    return response.bodyToMono(String.class);
                              })
                              .block();

                  return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                  // TODO: handle exception
                  return ResponseEntity.status(500)
                              .body(null);
            }

      }

}
