package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.document.BankAccount;
import com.nttdata.bankaccountservice.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller of Bank Account.
 */
@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    //Method to get all the bank account
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<BankAccount> findAll() {
        return bankAccountService.findAll();
    }

    //Method to insert a new bank account
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankAccount> register(@RequestBody BankAccount bankAccount) {
        return bankAccountService.register(bankAccount);
    }

    //Method to update a bank account
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankAccount> update(@RequestBody BankAccount bankAccount) {
        return bankAccountService.update(bankAccount);
    }

    //Method to get a bank account by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankAccount> findById(@PathVariable("id") String id) {
        return bankAccountService.findById(id);
    }

    //Method to delete a bank account
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id) {
        return bankAccountService.delete(id);
    }

}
