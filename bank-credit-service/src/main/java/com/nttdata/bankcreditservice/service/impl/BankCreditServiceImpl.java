package com.nttdata.bankcreditservice.service.impl;

import com.nttdata.bankcreditservice.document.BankCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.nttdata.bankcreditservice.repository.BankCreditRepository;
import com.nttdata.bankcreditservice.service.BankCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankCreditServiceImpl implements BankCreditService {

    @Autowired
    private BankCreditRepository bankCreditRepository;


    @Override
    public Flux<BankCredit> findAll() {
        return this.bankCreditRepository.findAll();
    }

    @Override
    public Mono<BankCredit> register(BankCredit bankCredit) {
        return this.bankCreditRepository.save(bankCredit);
    }

    @Override
    public Mono<BankCredit> update(BankCredit bankCredit) {
        return this.bankCreditRepository.save(bankCredit);
    }

    @Override
    public Mono<BankCredit> findById(String id) {
        return this.bankCreditRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.bankCreditRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return this.bankCreditRepository.existsById(id);
    }
}
