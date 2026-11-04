package com.vithgab.subscription_apis.clientsDTO;

import com.vithgab.subscription_apis.model.Client;

public record ClientResponseDTO(
    Long id,
    String name,
    String email
) {
    public static ClientResponseDTO fromClient(Client client) {
        return new ClientResponseDTO(
            client.getId(),
            client.getName(),
            client.getEmail()
        );
    }
}