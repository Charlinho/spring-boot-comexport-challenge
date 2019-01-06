package com.comexport.dtos;

import com.comexport.domains.Account;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountDto {

    @NotNull
    private Long numero;

    private String descricao;

    public static AccountDto parse(Account account) {
        AccountDto accountDto = new AccountDto();

        accountDto.setNumero(account.getNumber());
        accountDto.setDescricao(account.getDescription());

        return accountDto;
    }
}
