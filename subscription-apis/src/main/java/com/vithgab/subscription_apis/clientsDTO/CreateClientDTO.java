package com.vithgab.subscription_apis.clientsDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientDTO(        
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must have between 3 and 100 characters")
    String name,

    @NotBlank(message = "Email is required")        @Email(message = "Email is invalid")        @Size(max = 150, message = "Email must have at most 150 characters")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must have between 8 and 64 characters")
    String password
){
}
