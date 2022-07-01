package com.itransition.coursework.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Abdulqodir Ganiev 6/22/2022 1:45 AM
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationDto {
    @NotBlank(message = "Name cannot be null")
    private String fullName;

    @Email(message = "This field is for email")
    private String email;

    @Pattern(message = "Please follow password requirements",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,100}$")
    private String password;

    @Pattern(message = "Please follow password requirements",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,100}$")
    private String confirmPassword;
}
