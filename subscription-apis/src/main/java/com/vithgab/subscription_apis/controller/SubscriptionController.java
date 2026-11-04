package com.vithgab.subscription_apis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vithgab.subscription_apis.exception.ApiError;
import com.vithgab.subscription_apis.model.Subscription;
import com.vithgab.subscription_apis.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Subscriptions", description = "CRUD operations for subscriptions")

public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    } 

    @GetMapping("/{id}")
     @Operation(summary = "Get a subscription by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Subscription found"),
        @ApiResponse(responseCode = "404", description = "Subscription not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public Subscription getSubscriptionById(@PathVariable Long id){
        return subscriptionService.getSubscriptionById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new subscription")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Subscription created"),
        @ApiResponse(responseCode = "400", description = "Invalid data",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Client not found",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "The client already has a subscription with that name",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public String createSubscription(@RequestBody Subscription subscription){
        Subscription savedSubscription = subscriptionService.saveSubscription(subscription);
        return "Successfully saved subscription with ID: " + savedSubscription.getId();
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Filter subscriptions by exact name")
    @ApiResponse(responseCode = "200", description = "Subscriptions with that name (empty list if none)")
    public List<Subscription> getSubscriptionByName(@PathVariable String name){
        return subscriptionService.getSubscriptionByName(name);
    }

    @GetMapping("/paymentDay/{paymentDay}")
    @Operation(summary = "Filter subscriptions by payment day")
    @ApiResponse(responseCode = "200", description = "Subscriptions with that payment day (empty list if none)")
    public List<Subscription> getSubscriptionByPaymentDay(@PathVariable Integer paymentDay){
        return subscriptionService.getSubscriptionByPaymentDay(paymentDay);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subscription by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Subscription deleted"),
        @ApiResponse(responseCode = "404", description = "Subscription not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void deleteSubscription(@PathVariable Long id){
        subscriptionService.deleteSubscription(id);
    }
    
    @GetMapping("/subscription")
    @Operation(summary = "List all subscriptions")
    @ApiResponse(responseCode = "200", description = "All subscriptions (empty list if none)")
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update a subscription",
        description = "The owner client of the subscription does not change. There cannot be another subscription from the same client with the same name.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Subscription updated"),
        @ApiResponse(responseCode = "400", description = "Invalid data",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Subscription not found",
                content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "The client already has a subscription with that name",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<String> updatedSubscription(@RequestBody Subscription subscriptionDetails, @PathVariable Long id) {
        Subscription updatedSubscription = subscriptionService.updateSubscription(id, subscriptionDetails);
        return ResponseEntity.ok("Subscription updated successfully with ID: " + updatedSubscription.getId());
    }
}
