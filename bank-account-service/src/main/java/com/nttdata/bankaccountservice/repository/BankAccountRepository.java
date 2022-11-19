package com.nttdata.bankaccountservice.repository;

import com.nttdata.bankaccountservice.document.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Bank Account Repository.
 */
@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {
}
