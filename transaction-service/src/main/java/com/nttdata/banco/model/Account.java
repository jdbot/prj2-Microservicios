package com.nttdata.banco.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Account class.
 */
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Account {

    //account id
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
    //type of the bank account
    private String type;
}
