package com.nttdata.bankcreditservice.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bank-credits")
public class BankCredit {

    @Id
    private String id;
    //number credit of the bank credit
    private String numberCredit;
    //amount of the bank credit
    private Float amount;
    //end date of the bank credit
    private String endDate;
    //id of the client
    private String customerId;
    //full name of the client
    private String customerName;

    //@DocumentReference
    //type of the bank credit
    private String type;
}
