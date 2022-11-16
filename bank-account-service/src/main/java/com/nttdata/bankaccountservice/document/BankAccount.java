package com.nttdata.bankaccountservice.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bank-accounts")
public class BankAccount {

    @Id
    private String id;
    //number account of the bank account
    private String numberAccount;
    //amount of the bank account
    private Float amount;
    //end date of the bank account
    private String endDate;
    //id of the client
    private String customerId;
    //full name of the client
    private String customerName;

    //@DocumentReference
    //type of the bank account
    private String type;
}
