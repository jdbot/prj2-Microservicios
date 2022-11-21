package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.document.BankAccount;
import com.nttdata.bankaccountservice.dto.ClientDTO;
import com.nttdata.bankaccountservice.document.Transaction;
import com.nttdata.bankaccountservice.dto.TransactionBetweenAccountsDto;
import com.nttdata.bankaccountservice.service.BankAccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
        return bankAccountService.validateRegister(bankAccount);
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

    //Method to get a client by ID
    // @GetMapping("/findClientById/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name="client", fallbackMethod = "fallBackGetFindByClientId")
    public Mono<ClientDTO> findByClientId(@PathVariable("id") String id) {
        return bankAccountService.findClientById(id);
    }

    //Method to get a bank account by clientId and type
    @GetMapping("/findByCustomerIdAndType/{id}/{type}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BankAccount> findByCustomerIdAndType(@PathVariable("id") String customerId,
                                                     @PathVariable("type") String type) {
        return bankAccountService.findByCustomerIdAndType(customerId, type);
    }

    //Method fallback to client microservice
    public Mono<String> fallBackGetFindByClientId(String id, RuntimeException runtimeException){
        return Mono.just("the client microservice is not responding");
//        return Mono.just("limiter client timeout");
    }

    //Method to do a deposit
    @PutMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankAccount> doDeposit(@RequestBody Transaction transaction) {
        return bankAccountService.doDeposit(transaction);
    }

    //Method to do a withdrawl
    @PutMapping("/withdrawl")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankAccount> doWithdrawl(@RequestBody Transaction transaction) {
        return bankAccountService.doWithdrawl(transaction);
    }

    //Method to do a transaction between accounts
    @PutMapping("/tba")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankAccount> doTransactionBetweenAccounts(@RequestBody TransactionBetweenAccountsDto tba) {
        return bankAccountService.doTransactionBetweenAccounts(tba);
    }

}
