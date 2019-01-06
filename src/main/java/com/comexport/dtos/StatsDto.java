package com.comexport.dtos;

import com.comexport.domains.AccountingEntry;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Data
public class StatsDto {

    private BigDecimal soma;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal media;
    private Integer qtde;

    public static StatsDto getStats(List<AccountingEntry> accountingEntries) {
        StatsDto statsDto = new StatsDto();

        statsDto.setQtde(accountingEntries.size());
        statsDto.setSoma(accountingEntries.stream().map(AccountingEntry::getValue).reduce(BigDecimal::add).get());
        statsDto.setMax(accountingEntries.stream().map(AccountingEntry::getValue).max(Comparator.naturalOrder()).get());
        statsDto.setMin(accountingEntries.stream().map(AccountingEntry::getValue).min(Comparator.naturalOrder()).get());
        statsDto.setMedia(statsDto.getSoma().divide(BigDecimal.valueOf(statsDto.getQtde())));

        return statsDto;
    }
}
