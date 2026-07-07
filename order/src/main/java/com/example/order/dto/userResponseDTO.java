 package com.example.order.dto;

import com.example.order.model.UserRole;
import lombok.Data;

 @Data
 public class userResponseDTO {
     private String firstName;
     private String lastName;
     private String email;
     private String phoneNumber;
     private UserRole role;
     private addressDto address;
 }
