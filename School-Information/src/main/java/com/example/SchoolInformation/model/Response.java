package com.example.SchoolInformation.model;

import java.util.Date;

import com.example.SchoolInformation.utils.Meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {
      public Response(String correlationId, Date datetime, String statusCode, String message, Object userInfo,
                  String description) {
            this.data = new com.example.SchoolInformation.utils.Data();
            this.meta = new Meta();
            this.meta.setCorrelationId(correlationId);
            this.meta.setDatetime(datetime);
            this.data.setStatusCode(statusCode);
            this.data.setMessage(message);
            this.data.setDescription(description);
            this.data.setInfo(userInfo);

      }

      Meta meta;

      com.example.SchoolInformation.utils.Data data;
}
