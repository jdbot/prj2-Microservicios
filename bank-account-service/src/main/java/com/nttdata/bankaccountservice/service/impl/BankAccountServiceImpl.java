package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.document.BankAccount;
import com.nttdata.bankaccountservice.dto.ClientDTO;
import com.nttdata.bankaccountservice.repository.BankAccountRepository;
import com.nttdata.bankaccountservice.service.AccountTypeService;
import com.nttdata.bankaccountservice.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

//    public BankAccountServiceImpl(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8090").build();
////        this.webClient = webClientBuilder.baseUrl("http://client-service").build();
//    }

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

//        LOGGER.info("validateClientPersonal");
//        return client.handle((c, validClientEmpType) -> {
//            if (c.getType().equals("personal") & bam != null ) {
//                LOGGER.error("ya existe este tipo de cuenta bancaria para este cliente");
//                validClientEmpType.error( new RuntimeException("validacion 1"));
//            } else {
//                LOGGER.info("validateClientPersonal ok");
//                validClientEmpType.next(c);
//            }
//        });
//    }

    /*
    public Mono<ClientDTO> validateClientPersonal(Mono<ClientDTO> client, String customerId, String type){
        Flux<BankAccount> baf = this.bankAccountRepository.findByCustomerIdAndType(
                customerId, type);
        Mono<BankAccount> bam = baf.next();

        LOGGER.info("validateClientPersonal");
        return client.handle((c, validClientEmpType) -> {
            if (c.getType().equals("personal") & bam != null ) {
                LOGGER.error("ya existe este tipo de cuenta bancaria para este cliente");
                validClientEmpType.error( new RuntimeException("validacion 1"));
            } else {
                LOGGER.info("validateClientPersonal ok");
                validClientEmpType.next(c);
            }
        });
    }

    public Mono<ClientDTO> validateClientEmpresarial(Mono<ClientDTO> client, String type){
        Mono<AccountType> atm = this.accountTypeService.findById(type).filter(obj ->
                obj.getCode().equals("1") || obj.getCode().equals("2")
        );
        LOGGER.info("validateClientEmpresarial "+client);
        return client.handle((c, validClientEmpType) -> {
            if (c.getType().equals("empresarial") & atm != null ) {
                LOGGER.error("Cliente de tipo empresarial solo puede crear cuentas corrientes");
                validClientEmpType.error( new RuntimeException("validacion 2"));
            } else {
                LOGGER.info("validateClientEmpresarial ok");
                validClientEmpType.next(c);
            }
        });
    }*/

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



}
