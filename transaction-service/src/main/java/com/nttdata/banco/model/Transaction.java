package com.nttdata.banco.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Document (collection = "transactions")
public class Transaction {
    @Id
    public String id;

    //Date of the transaction
    private String transactionDate;
    //Amount of the transaction
    private double amount;
    //Transaction's type: Deposit or Withdrawl
    private String type;
    //Associated account's ID
    private String idAccount;

}
