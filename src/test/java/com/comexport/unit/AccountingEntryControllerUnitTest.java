package com.comexport.unit;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.comexport.controllers.AccountingEntryController;
import com.comexport.dtos.AccountDto;
import com.comexport.dtos.AccountingEntryDto;
import com.comexport.dtos.StatsDto;
import com.comexport.services.AccountingEntryService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AccountingEntryControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountingEntryController accountingEntryController;

    @MockBean
    AccountingEntryService accountingEntryService;

    @Test
    public void givenANewAccountingEntry_whenSuccessInserted_thenReturnUUID() throws Exception {
        String UUID = "123456";

        when(accountingEntryService.save(any(AccountingEntryDto.class))).thenReturn(UUID);

        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();

        accountingEntryDto.setValor(new BigDecimal(1000));
        accountingEntryDto.setData("2019-01-01");

        AccountDto accountDto = new AccountDto();
        accountDto.setNumero(1000L);

        accountingEntryDto.setContaContabil(accountDto);

        mockMvc.perform(post("/lancamentos-contabeis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(accountingEntryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", is(123456)));
    }

    @Test
    public void givenAccountNumber_whenAccountNumberWasFound_thenReturnAccountingEntries() throws Exception {
        List<AccountingEntryDto> accountingEntryDtos = new ArrayList<>();

        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();

        accountingEntryDto.setValor(new BigDecimal(1000));
        accountingEntryDto.setData("2019-01-01");

        AccountDto accountDto = new AccountDto();
        accountDto.setNumero(1000L);

        accountingEntryDto.setContaContabil(accountDto);

        accountingEntryDtos.add(accountingEntryDto);

        when(accountingEntryService.findAccounts(any(Long.class))).thenReturn(accountingEntryDtos);

        mockMvc.perform(get("/lancamentos-contabeis/?contaContabil=" + 1000).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].data", is("2019-01-01")));
    }

    @Test
    public void givenAccountNumber_whenAccountNumberWasFound_thenReturnStatsFromAccountNumber() throws Exception {
        StatsDto statsDto = new StatsDto();

        statsDto.setQtde(2);
        statsDto.setMedia(new BigDecimal(1000));
        statsDto.setSoma(new BigDecimal(2000));
        statsDto.setMin(new BigDecimal(1000));
        statsDto.setMax(new BigDecimal(1000));

        when(accountingEntryService.getStats(any(Long.class))).thenReturn(statsDto);

        mockMvc.perform(get("/lancamentos-contabeis/_stats/?contaContabil=" + 1000).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.soma", is(2000)))
                .andExpect(jsonPath("$.min", is(1000)))
                .andExpect(jsonPath("$.max", is(1000)))
                .andExpect(jsonPath("$.media", is(1000)))
                .andExpect(jsonPath("$.qtde", is(2)));
    }

    @Test
    public void givenUUID_whenUUIDWasFound_thenReturnAccountingEntry() throws Exception {
        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();

        accountingEntryDto.setValor(new BigDecimal(1000));
        accountingEntryDto.setData("2019-01-01");

        AccountDto accountDto = new AccountDto();
        accountDto.setNumero(1000L);

        accountingEntryDto.setContaContabil(accountDto);

        when(accountingEntryService.findAccountingEntry(any(String.class))).thenReturn(accountingEntryDto);

        mockMvc.perform(get("/lancamentos-contabeis/123456").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", is("2019-01-01")))
                .andExpect(jsonPath("$.valor", is(1000)));
    }
}
