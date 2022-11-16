package com.nttdata.bankcreditservice.controller;

import com.nttdata.bankcreditservice.document.BankCredit;
import com.nttdata.bankcreditservice.service.BankCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bankCredit")
public class BankCreditController {

    @Autowired
    private BankCreditService bankCreditService;

    //Method to get all the bank credits
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<BankCredit> findAll(){
        return bankCreditService.findAll();
    }

    //Method to insert a new bank credit
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankCredit> register(@RequestBody BankCredit bankCredit) {
        return bankCreditService.register(bankCredit);
    }

    //Method to update a bank credit
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankCredit> update(@RequestBody BankCredit bankCredit) {
        return bankCreditService.update(bankCredit);
    }

    //Method to get a bank credit by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankCredit> findById(@PathVariable("id") String id){
        return bankCreditService.findById(id);
    }

    //Method to delete a bank credit
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id){
        return bankCreditService.delete(id);
    }

}
