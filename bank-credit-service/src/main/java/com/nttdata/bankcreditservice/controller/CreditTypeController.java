package com.nttdata.bankcreditservice.controller;

import com.nttdata.bankcreditservice.document.CreditType;
import com.nttdata.bankcreditservice.service.CreditTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/creditType")
public class CreditTypeController {

    @Autowired
    private CreditTypeService creditTypeService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<CreditType> findAll(){
        return creditTypeService.findAll();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditType> register(@RequestBody CreditType creditType) {
        return creditTypeService.register(creditType);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CreditType> update(@RequestBody CreditType creditType) {
        return creditTypeService.update(creditType);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CreditType> findById(@PathVariable("id") String id){
        return creditTypeService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id){
        return creditTypeService.delete(id);
    }

}
