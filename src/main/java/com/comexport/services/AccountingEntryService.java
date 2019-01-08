package com.comexport.services;

import com.comexport.domains.AccountingEntry;
import com.comexport.dtos.AccountDto;
import com.comexport.dtos.AccountingEntryDto;
import com.comexport.dtos.StatsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountingEntryService {

    private List<AccountingEntry> accountingEntries = new ArrayList<>();

    public String save(AccountingEntryDto accountingEntryDto) {
        AccountingEntry accountingEntry = generateUUID(AccountingEntry.parseFromDto(accountingEntryDto));

        this.addAccountingEntry(accountingEntry);

        return accountingEntry.getId();
    }

    public AccountingEntryDto findAccountingEntry(String id) {
        if (this.accountingEntries.isEmpty()) {
            return null;
        }

        return AccountingEntryDto.parse(this.accountingEntries
                .stream()
                .filter((accounting) -> accounting.getId().equals(id))
                .findFirst().orElse(null));
    }

    public List<AccountingEntryDto> findAccounts(Long number) {
        if (this.accountingEntries.isEmpty()) {
            return null;
        }

        return AccountingEntryDto.parseToList(
                this.accountingEntries.stream()
                    .filter((accounting) -> accounting.getAccount().getNumber().equals(number))
                        .collect(Collectors.toList()));
    }

    public StatsDto getStats(Long accountNumber) {
        if (accountNumber != null) {
            return getStatsByAccountNumber(accountNumber);
        }
        return StatsDto.getStats(this.accountingEntries);
    }

    public void cleanAccountingEntries() {
        this.accountingEntries = new ArrayList<>();
    }

    private StatsDto getStatsByAccountNumber(Long accountNumber) {
        return StatsDto
                .getStats(this.accountingEntries
                        .stream()
                        .filter((accountingEntry) -> accountingEntry.getAccount().getNumber().equals(accountNumber))
                        .collect(Collectors.toList()));
    }

    private AccountingEntry generateUUID(AccountingEntry accountingEntry) {
        accountingEntry.setId(UUID.randomUUID().toString());
        return accountingEntry;
    }

    private void addAccountingEntry(AccountingEntry accountingEntry) {
        this.accountingEntries.add(accountingEntry);
    }

}
