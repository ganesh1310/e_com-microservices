 package com.eComApp.user.dto;

import com.eComApp.user.models.UserRole;
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
