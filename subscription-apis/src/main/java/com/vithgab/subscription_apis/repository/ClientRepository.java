package com.vithgab.subscription_apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vithgab.subscription_apis.model.Client;

public interface ClientRepository  extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

}
