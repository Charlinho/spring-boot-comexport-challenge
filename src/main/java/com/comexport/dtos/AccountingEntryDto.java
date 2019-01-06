package com.comexport.dtos;

import com.comexport.domains.AccountingEntry;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccountingEntryDto {

    @NotNull
    private String data;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private AccountDto contaContabil;

    public static AccountingEntryDto parse(AccountingEntry accountingEntry) {
        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();

        accountingEntryDto.setValor(accountingEntry.getValue());
        accountingEntryDto.setData(accountingEntry.getDate().toString());
        accountingEntryDto.setContaContabil(AccountDto.parse(accountingEntry.getAccount()));

        return accountingEntryDto;
    }

    public static List<AccountingEntryDto> parseToList(List<AccountingEntry> accountingEntries) {
        List<AccountingEntryDto> accountingEntryDtos = new ArrayList<>();

        accountingEntries
                .forEach((accountingEntry) -> accountingEntryDtos.add(AccountingEntryDto.parse(accountingEntry)));

        return accountingEntryDtos;
    }
}
