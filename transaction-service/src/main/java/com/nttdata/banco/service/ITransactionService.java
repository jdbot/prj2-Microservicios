package com.nttdata.banco.service;

import com.nttdata.banco.dto.TransactionBetweenAccountsDto;
import com.nttdata.banco.model.Account;
import com.nttdata.banco.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Transaction service interface.
 */
public interface ITransactionService {


    public Flux<Transaction> findAll();

    public Mono<Transaction> register(Transaction transaction);

    public Mono<Transaction> update(Transaction transaction);

    public Mono<Void> delete(String id);

    public Mono<Transaction> findById(String id);

    public Mono<Transaction> doDeposit(Transaction transaction);

    public Mono<Transaction> doWithdrawl(Transaction transaction);

    public Mono<Transaction> doTransactionBetweenAccounts(TransactionBetweenAccountsDto tBetweenDto);

    public Mono<Account> findAccountById(String id);
}
