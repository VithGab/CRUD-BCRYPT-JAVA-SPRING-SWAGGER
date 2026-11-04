package com.vithgab.subscription_apis.clientsDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientUpdateDTO(
    @NotBlank (message = "Name is required")
    @Size (min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @NotBlank (message = "Email is required")
    @Size ( max = 150, message = "Email must have at most 150 characters")
    @Email (message = "Email must be a valid email address")
    String email
){
}
