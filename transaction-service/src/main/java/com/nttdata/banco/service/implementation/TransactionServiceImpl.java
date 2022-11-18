package com.nttdata.banco.service.implementation;

import com.nttdata.banco.dto.TransactionBetweenAccountsDto;
import com.nttdata.banco.model.Account;
import com.nttdata.banco.model.Transaction;
import com.nttdata.banco.repository.TransactionRepository;
import com.nttdata.banco.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Transaction service implementation.
 */
@Service
public class TransactionServiceImpl implements ITransactionService {

    private final WebClient webClient;

    public TransactionServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> register(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Void> delete(String id) {
        return transactionRepository.deleteById(id);
    }

    @Override
    public Mono<Transaction> findById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Mono<Transaction> doDeposit(Transaction transaction) {
        transaction.setType("deposit");
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> doWithdrawl(Transaction transaction) {
        transaction.setType("withdrawl");
        return findAccountById(transaction.getIdAccount()).flatMap(x -> {
            if(x.getAmount() - transaction.getAmount() >= 0) {
                return transactionRepository.save(transaction);
            }else{
                return Mono.empty();
            }
        });

    }

    @Override
    public Mono<Transaction> doTransactionBetweenAccounts(TransactionBetweenAccountsDto tBetweenDto) {
        Transaction tSender = new Transaction(null,"18/11/2022",tBetweenDto.getAmmount(),null,tBetweenDto.getSenderAccountId());
        return doWithdrawl(tSender).flatMap(x -> {
            Transaction tReceptor = new Transaction(null,"18/11/2022",tBetweenDto.getAmmount(),null,tBetweenDto.getReceptorAccountId());
            return doDeposit(tReceptor);
        });
    }

    @Override
    public Mono<Account> findAccountById(String id) {
        return this.webClient.get().uri("/bankAccount/{id}", id).retrieve().bodyToMono(Account.class);
    }


}
