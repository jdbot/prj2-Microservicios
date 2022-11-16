package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.document.AccountType;
import com.nttdata.bankaccountservice.service.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accountType")
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<AccountType> findAll(){
        return accountTypeService.findAll();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountType> register(@RequestBody AccountType accountType) {
        return accountTypeService.register(accountType);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AccountType> update(@RequestBody AccountType accountType) {
        return accountTypeService.update(accountType);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AccountType> findById(@PathVariable("id") String id){
        return accountTypeService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id){
        return accountTypeService.delete(id);
    }


}
