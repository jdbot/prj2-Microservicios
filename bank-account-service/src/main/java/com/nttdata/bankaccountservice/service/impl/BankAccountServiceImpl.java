package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.document.BankAccount;
import com.nttdata.bankaccountservice.dto.ClientDTO;
import com.nttdata.bankaccountservice.document.Transaction;
import com.nttdata.bankaccountservice.dto.TransactionBetweenAccountsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.nttdata.bankaccountservice.repository.BankAccountRepository;
import com.nttdata.bankaccountservice.service.AccountTypeService;
import com.nttdata.bankaccountservice.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Bank Account Service Implementation.
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);


    @Autowired
    private WebClient.Builder webClient;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private AccountTypeService accountTypeService;

    @Override
    public Flux<BankAccount> findAll() {
        return this.bankAccountRepository.findAll();
    }

    @Override
    public Mono<BankAccount> register(BankAccount bankAccount) {
//        return validateRegister(bankAccount).switchIfEmpty(this.bankAccountRepository.save(bankAccount));
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
    public Mono<ClientDTO> findClientById(String clientId) {
        LOGGER.info("Consulted client from the bank-account-service");
        Mono<ClientDTO> client =this.webClient.build().get().uri("/client/{id}", clientId)
                .retrieve().bodyToMono(ClientDTO.class);
        LOGGER.info("Consulted client from the bank-account-service");
        return client;
    }

    @Override
    public Mono<BankAccount> validateRegister(BankAccount bankAccount) {
        return this.webClient.build().get().uri("/client/{id}", bankAccount.getCustomerId())
            .retrieve()
            .bodyToMono(ClientDTO.class)
            .flatMap(dc -> {
                if (dc.getType().equals("personal")) {
                    return findByCAndT(bankAccount.getCustomerId(), bankAccount.getType())
                        .flatMap(x-> {
                            LOGGER.error("ya existe este tipo de cuenta bancaria para este cliente");
                            throw new RuntimeException("ya existe este tipo de cuenta bancaria para este cliente");
                        });
                } else {
                    return this.accountTypeService.findById(bankAccount.getType()).filter(obj ->
                        obj.getCode().equals("1") || obj.getCode().equals("2"))
                            .flatMap(x-> {
                                LOGGER.error("Cliente de tipo empresarial solo puede crear cuentas corrientes");
                                throw new RuntimeException("Cliente de tipo empresarial solo puede crear cuentas corrientes");
                            });
                }

            });
    }

    @Override
    public Flux<BankAccount> findByCustomerIdAndType(String customerId, String type){
        return this.bankAccountRepository.findByCustomerIdAndType(
                customerId, type);
    }

    public Mono<BankAccount> findByCAndT(String customerId, String type){
        LOGGER.info("validateBankAccount");
        Flux<BankAccount> baf = this.bankAccountRepository.findByCustomerIdAndType(
                customerId, type);
        Mono<BankAccount> bam = baf.next();
        return bam;
    }

    public Mono<BankAccount> doDeposit(Transaction transaction) {
        return findById(transaction.getIdAccount()).flatMap(x -> {
            float newAmount = x.getAmount() + transaction.getAmount();
            transaction.setType("deposit");
            transaction.setIdClient(x.getCustomerId());
            transaction.setAccountAmount(newAmount);
            x.setAmount(newAmount);
            x.setNumberOfTransactions(x.getNumberOfTransactions()+1);
            return this.webClient.build().post().uri("/transaction/").
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
                return this.webClient.build().post().uri("/transaction/").
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
