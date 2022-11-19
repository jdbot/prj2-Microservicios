package com.nttdata.bankaccountservice.dto;

import com.nttdata.bankaccountservice.document.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class BankAccountDTO {

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

        //id of the bank type
        private String typeId;

        private AccountType type;

}
