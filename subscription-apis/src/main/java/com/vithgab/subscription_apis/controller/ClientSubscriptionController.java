package com.vithgab.subscription_apis.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vithgab.subscription_apis.exception.ApiError;
import com.vithgab.subscription_apis.model.Subscription;
import com.vithgab.subscription_apis.service.SubscriptionService;
import com.vithgab.subscription_apis.subscriptionsDTO.SubscriptionDTO;
import com.vithgab.subscription_apis.subscriptionsDTO.SubscriptionResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients/{clientId}/subscriptions")
@Tag(name = "Client subscriptions", description = "Operations related to client subscriptions")
public class ClientSubscriptionController {

    private final SubscriptionService subscriptionService;

    public ClientSubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    @PostMapping
    @Operation(summary = "Create a new subscription for a client",
        description = "Specify the client ID in the path and provide subscription details in the request body.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Subscription created"),
        @ApiResponse(responseCode = "400", description = "Invalid data",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Client not found",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "The client already has a subscription with that name",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<SubscriptionResponseDTO> create(@PathVariable Long clientId,
            @Valid @RequestBody SubscriptionDTO dto) {
        Subscription saved = subscriptionService.createForClient(clientId, dto);
        URI location = URI.create("/subscriptions/" + saved.getId());
        return ResponseEntity.created(location).body(SubscriptionResponseDTO.from(saved));
    }

    @GetMapping
     @Operation(summary = "List client subscriptions")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client subscriptions (empty list if none)"),
        @ApiResponse(responseCode = "404", description = "Client not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public List<SubscriptionResponseDTO> list(@PathVariable Long clientId) {
        return subscriptionService.findByClient(clientId).stream()
                .map(SubscriptionResponseDTO::from).toList();
    }

    

}
