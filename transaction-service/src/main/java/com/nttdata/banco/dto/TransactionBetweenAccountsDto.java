package com.nttdata.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transaction between accounts DTO.
 */
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class TransactionBetweenAccountsDto {

    private String senderAccountId;
    private String receptorAccountId;
    private Double ammount;
}
