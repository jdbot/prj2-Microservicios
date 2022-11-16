package com.nttdata.banco.controller;

import com.nttdata.banco.model.Client;
import com.nttdata.banco.service.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    //Method to get all the clients
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Client> findAll(){
        return clientService.findAll();
    }

    //Method to insert a new client
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Client> register(@RequestBody Client client){
        return  clientService.register(client);
    }

    //Method to update a client
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Client> modify(@RequestBody Client client){
        return  clientService.update(client);
    }

    //Method to get a client by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Client> findById(@PathVariable("id") String id){
        return clientService.findById(id);
    }

    //Method to delete a client
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id){
        return clientService.delete(id);
    }
}
