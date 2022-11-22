package com.nttdata.banco.service.implementation;

import com.nttdata.banco.model.Transaction;
import com.nttdata.banco.repository.TransactionRepository;
import com.nttdata.banco.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transaction service implementation.
 */
@Service
public class TransactionServiceImpl implements ITransactionService {
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
    public Flux<Transaction> findTransactionsByAccountId(String idAccount) {
        return transactionRepository.findAllByIdAccount(idAccount);
    }

    @Override
    public Mono<Map<String, Double>> makeAmountAvgReport(String idClient) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        YearMonth monthYear = YearMonth.now();
        return transactionRepository.findAllByIdClient(idClient).filter(x ->
                YearMonth.from(LocalDate.parse(x.getTransactionDate(), formatter)).equals(monthYear)).
                collect(Collectors.groupingBy(Transaction::getIdAccount,Collectors.averagingDouble(Transaction::getAccountAmount)));
    }


}
