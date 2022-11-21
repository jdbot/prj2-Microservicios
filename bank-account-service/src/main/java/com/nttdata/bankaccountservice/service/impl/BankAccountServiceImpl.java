package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.document.BankAccount;
import com.nttdata.bankaccountservice.document.Transaction;
import com.nttdata.bankaccountservice.dto.TransactionBetweenAccountsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.nttdata.bankaccountservice.repository.BankAccountRepository;
import com.nttdata.bankaccountservice.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final WebClient webClient;

    public BankAccountServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Override
    public Flux<BankAccount> findAll() {
        return this.bankAccountRepository.findAll();
    }

    @Override
    public Mono<BankAccount> register(BankAccount bankAccount) {
        return this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public Mono<BankAccount> update(BankAccount bankAccount) {
        return this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public Mono<BankAccount> findById(String id) {
        return this.bankAccountRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        return bankAccountRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return this.bankAccountRepository.existsById(id);
    }

    @Override
    public Mono<BankAccount> doDeposit(Transaction transaction) {
        return findById(transaction.getIdAccount()).flatMap(x -> {
            float newAmount = x.getAmount() + transaction.getAmount();
            transaction.setType("deposit");
            transaction.setIdClient(x.getCustomerId());
            transaction.setAccountAmount(newAmount);
            x.setAmount(newAmount);
            x.setNumberOfTransactions(x.getNumberOfTransactions()+1);
            return this.webClient.post().uri("/transaction/").
                    header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).
                    body(Mono.just(transaction), Transaction.class).
                    retrieve().
                    bodyToMono(Transaction.class).
                    flatMap(y -> update(x));
        });
    }

    @Override
    public Mono<BankAccount> doWithdrawl(Transaction transaction) {
        return findById(transaction.getIdAccount()).flatMap(x -> {
            float newAmount = x.getAmount() - transaction.getAmount();
            if( newAmount >= 0) {
                transaction.setType("withdrawl");
                transaction.setIdClient(x.getCustomerId());
                transaction.setAccountAmount(newAmount);
                x.setAmount(newAmount);
                x.setNumberOfTransactions(x.getNumberOfTransactions()+1);
                return this.webClient.post().uri("/transaction/").
                        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).
                        body(Mono.just(transaction), Transaction.class).
                        retrieve().
                        bodyToMono(Transaction.class).
                        flatMap(y -> update(x));
            }else{
                return Mono.empty();
            }
        });
    }

    @Override
    public Mono<BankAccount> doTransactionBetweenAccounts(TransactionBetweenAccountsDto t) {
        Transaction tSender = new Transaction(t.getTransactionDate(), t.getAmount(), "withdrawl",null, t.getSenderAccountId(), 0);
        Transaction tReceptor = new Transaction(t.getTransactionDate(), t.getAmount(), "deposit", null, t.getReceptorAccountId(), 0);
        return doWithdrawl(tSender).flatMap(x -> doDeposit(tReceptor));
    }
}
