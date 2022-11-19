package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.document.AccountType;
import com.nttdata.bankaccountservice.document.BankAccount;
import com.nttdata.bankaccountservice.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.nttdata.bankaccountservice.service.BankAccountService;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    //Method to get all the bank account
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<BankAccount> findAll(){
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
    public Mono<BankAccount> findById(@PathVariable("id") String id){
        return bankAccountService.findById(id);
    }

    //Method to delete a bank account
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id){
        return bankAccountService.delete(id);
    }

    @GetMapping("/findClientById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDTO> findByClientId(@PathVariable("id") String id){
        return bankAccountService.findClientById(id);
    }

    @GetMapping("/findByCustomerIdAndType/{id}/{type}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BankAccount> findByCustomerIdAndType(@PathVariable("id") String customerId, @PathVariable("type") String type){
        return bankAccountService.findByCustomerIdAndType(customerId, type);
    }

}
