package com.nttdata.banco.controller;

import com.nttdata.banco.model.Transaction;
import com.nttdata.banco.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService TransactionService;

    //Method to get all the transactions
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Transaction> findAll(){
        return TransactionService.findAll();
    }

    //Method to insert a new transaction
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Transaction> register(@RequestBody Transaction Transaction){
        return  TransactionService.register(Transaction);
    }

    //Method to update a transaction
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Transaction> modify(@RequestBody Transaction Transaction){
        return  TransactionService.update(Transaction);
    }

    //Method to get a transaction by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Transaction> findById(@PathVariable("id") String id){
        return TransactionService.findById(id);
    }

    //Method to delete a transaction
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id){
        return TransactionService.delete(id);
    }
}
