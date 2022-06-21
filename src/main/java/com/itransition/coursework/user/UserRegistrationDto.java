package com.itransition.coursework.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Abdulqodir Ganiev 6/22/2022 1:45 AM
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationDto {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
}
