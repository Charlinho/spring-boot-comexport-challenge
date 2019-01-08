package com.comexport.controllers;

import com.comexport.domains.AccountingEntry;
import com.comexport.dtos.AccountingEntryDto;
import com.comexport.dtos.StatsDto;
import com.comexport.services.AccountingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lancamentos-contabeis")
public class AccountingEntryController {

    @Autowired
    private AccountingEntryService accountingEntryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String newAccountingEntry(@RequestBody AccountingEntryDto accountingEntryDto) {
      return accountingEntryService.save(accountingEntryDto);
    }

    @GetMapping()
    public List<AccountingEntryDto> getAccount(@RequestParam(value="contaContabil") Long contaContabil) {
        return accountingEntryService.findAccounts(contaContabil);
    }

    @GetMapping("{id}")
    public AccountingEntryDto getAccountingEntry(@PathVariable String id) {
        return accountingEntryService.findAccountingEntry(id);
    }

    @GetMapping("/_stats")
    public StatsDto getStats(@RequestParam(value="contaContabil", required = false) Long contaContabil) {
        return accountingEntryService.getStats(contaContabil);
    }
}
