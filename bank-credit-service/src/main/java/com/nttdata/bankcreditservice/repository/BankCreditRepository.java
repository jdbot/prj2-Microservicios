package com.nttdata.bankcreditservice.repository;

import com.nttdata.bankcreditservice.document.BankCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCreditRepository extends ReactiveMongoRepository<BankCredit, String> {
}
