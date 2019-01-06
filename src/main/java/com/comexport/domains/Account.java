package com.comexport.domains;

import com.comexport.dtos.AccountDto;
import lombok.Data;

@Data
public class Account {

    private Long number;
    private String description;

    public static Account parseFromDto(AccountDto accountDto) {
        Account account = new Account();

        account.setNumber(accountDto.getNumero());
        account.setDescription(accountDto.getDescricao());

        return account;
    }
}
