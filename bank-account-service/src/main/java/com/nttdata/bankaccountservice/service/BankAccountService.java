package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.document.AccountType;
import com.nttdata.bankaccountservice.document.BankAccount;
import com.nttdata.bankaccountservice.dto.ClientDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Account service interface.
 */
public interface BankAccountService {

    Flux<BankAccount> findAll();

    Mono<BankAccount> register(BankAccount bankAccount);

    Mono<BankAccount> update(BankAccount bankAccount);

    Mono<BankAccount> findById(String id);

    Mono<Void> delete(String id);

    Mono<Boolean> existsById(String id);

    Mono<ClientDTO> findClientById(String clientId);
    Flux<BankAccount> findByCustomerIdAndType(String customerId, String type);


}
