package com.vithgab.subscription_apis.controller;

import java.net.URI;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vithgab.subscription_apis.clientsDTO.ChangePasswordDTO;
import com.vithgab.subscription_apis.clientsDTO.ClientResponseDTO;
import com.vithgab.subscription_apis.clientsDTO.ClientUpdateDTO;
import com.vithgab.subscription_apis.clientsDTO.CreateClientDTO;
import com.vithgab.subscription_apis.exception.ApiError;
import com.vithgab.subscription_apis.model.Client;
import com.vithgab.subscription_apis.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Create a new client",
        description = "The password is stored with BCrypt and is never returned. The email is unique (case-insensitive) and the password must comply with the security policy.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Client created (Location header points to the new resource)"),
        @ApiResponse(responseCode = "400", description = "Invalid data (empty field, invalid email, size, etc.)",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "Email already registered",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "422", description = "Password out of security policy (list of issues comes in details)",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ClientResponseDTO> CreateClient(@Valid @RequestBody CreateClientDTO dto) {
        Client SavedClient = clientService.createClient(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(SavedClient.getId())
                .toUri();
        return ResponseEntity.created(location).body(ClientResponseDTO.fromClient(SavedClient));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a client by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client found"),
        @ApiResponse(responseCode = "404", description = "Client not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(ClientResponseDTO.fromClient(clientService.findById(id)));
    }
    
    @DeleteMapping ("/{id}")
    @Operation(summary = "Delete a client by ID",
        description = "The client can only be deleted if it has no subscriptions.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Client deleted"),
        @ApiResponse(responseCode = "404", description = "Client not found",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "422", description = "Client has subscriptions and cannot be deleted",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Update client password",
        description = "Requires the correct current password; the new password must be different from the current one and comply with the security policy.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Password updated"),
        @ApiResponse(responseCode = "400", description = "Invalid data",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Client not found",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "422", description = "Current password is incorrect, new password is the same as the current one or does not comply with the policy",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> updateClientPassword(@PathVariable Long id,
            @Valid @RequestBody ChangePasswordDTO dto) {
        clientService.changePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client name and email")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client updated"),
        @ApiResponse(responseCode = "400", description = "Invalid data",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Client not found",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "E-mail already used by another client",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id,
            @Valid @RequestBody ClientUpdateDTO dto) {
        return ResponseEntity.ok(ClientResponseDTO.fromClient(clientService.update(id, dto)));
    }

}