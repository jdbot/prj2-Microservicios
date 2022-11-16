package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.document.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {

    Flux<BankAccount> findAll();
    Mono<BankAccount> register(BankAccount bankAccount);
    Mono<BankAccount> update(BankAccount bankAccount);
    Mono<BankAccount> findById(String id);
    Mono<Void> delete(String id);
    Mono<Boolean> existsById(String id);

}
