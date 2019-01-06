package com.comexport.domains;

import com.comexport.dtos.AccountingEntryDto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountingEntry {

    private String id;
    private LocalDate date;
    private Account account;
    private BigDecimal value;

    public static AccountingEntry parseFromDto(AccountingEntryDto accountingEntryDto) {
        AccountingEntry accountingEntry = new AccountingEntry();

        accountingEntry.setValue(accountingEntryDto.getValor());
        accountingEntry.setDate(LocalDate.parse(accountingEntryDto.getData()));
        accountingEntry.setAccount(Account.parseFromDto(accountingEntryDto.getContaContabil()));

        return accountingEntry;
    }
}
