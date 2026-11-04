package com.vithgab.subscription_apis.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vithgab.subscription_apis.clientsDTO.ChangePasswordDTO;
import com.vithgab.subscription_apis.clientsDTO.ClientUpdateDTO;
import com.vithgab.subscription_apis.clientsDTO.CreateClientDTO;
import com.vithgab.subscription_apis.exception.BusinessRuleException;
import com.vithgab.subscription_apis.exception.ConflictException;
import com.vithgab.subscription_apis.exception.ResourceNotFoundException;
import com.vithgab.subscription_apis.model.Client;
import com.vithgab.subscription_apis.repository.ClientRepository;
import com.vithgab.subscription_apis.repository.ClientSpecifications;
import com.vithgab.subscription_apis.repository.SubscriptionRepository;

@Service 
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicy passwordPolicy;
    private final SubscriptionRepository subscriptionRepository;

    public ClientService(ClientRepository clientRepository,
        SubscriptionRepository subscriptionRepository,
        PasswordEncoder passwordEncoder,
        PasswordPolicy passwordPolicy) {
            this.clientRepository = clientRepository;
            this.passwordEncoder = passwordEncoder;
            this.passwordPolicy = passwordPolicy;
            this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public Client createClient(CreateClientDTO dto) {
        String name = dto.name().trim();
        String email = normalizeEmail(dto.email());

        if (clientRepository.existsByEmail(email)) {
            throw new ConflictException("Email already exists");
        }
        passwordPolicy.validate(dto.password(), dto.name(), dto.email());

        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPasswordHash(passwordEncoder.encode(dto.password()));
        client.setPasswordChangedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Client> getAllClients(String name, String email, Pageable pageable) {
        return clientRepository.findAll(ClientSpecifications.filter(name, email), pageable);
    }

        @Transactional
    public Client update(Long id, ClientUpdateDTO dto) {
        Client client = findById(id);

        String email = normalizeEmail(dto.email());
        if (clientRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ConflictException("Já existe outro cliente com o e-mail: " + email);
        }

        client.setName(dto.name().trim());
        client.setEmail(email);
        return clientRepository.save(client);
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordDTO dto) {
        Client client = findById(id);

        if (!passwordEncoder.matches(dto.currentPassword(), client.getPasswordHash())) {
            throw new BusinessRuleException("Current password is incorrect.");
        }
        if (passwordEncoder.matches(dto.newPassword(), client.getPasswordHash())) {
            throw new BusinessRuleException("The new password must be different from the current one.");
        }
        passwordPolicy.validate(dto.newPassword(), client.getName(), client.getEmail());

        client.setPasswordHash(passwordEncoder.encode(dto.newPassword()));
        client.setPasswordChangedAt(LocalDateTime.now());
        clientRepository.save(client);
    }

    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }


}
