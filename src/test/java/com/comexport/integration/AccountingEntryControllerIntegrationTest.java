package com.comexport.integration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.comexport.dtos.AccountDto;
import com.comexport.dtos.AccountingEntryDto;
import com.comexport.services.AccountingEntryService;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AccountingEntryControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountingEntryService accountingEntryService;


    @Before
    public void setup() {
        this.accountingEntryService.cleanAccountingEntries();
    }

    @Test
    public void givenANewAccountingEntry_whenSuccessInserted_thenReturnStatus201() throws Exception {
        mvc.perform(post("/lancamentos-contabeis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(getAccountingEntryDto())))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenUUID_whenUUIDWasFound_thenReturnAccountingEntry() throws Exception {
        String UUID = createANewAccountingEntry(getAccountingEntryDto());

        mvc.perform(get("/lancamentos-contabeis/" + UUID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", is("2019-01-01")))
                .andExpect(jsonPath("$.valor", is(1000)));
    }

    @Test
    public void givenAccountNumber_whenAccountNumberWasFound_thenReturnAccountingEntries() throws Exception {
        createANewAccountingEntry(getAccountingEntryDto());
        createANewAccountingEntry(getAccountingEntryDto());

        mvc.perform(get("/lancamentos-contabeis/?contaContabil=" + 1000).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].data", is("2019-01-01")))
                .andExpect(jsonPath("$[1].valor", is(1000)));

    }

    @Test
    public void whenAccountNumberWasNotInformed_thenReturnAllStats() throws Exception {
        createANewAccountingEntry(getAccountingEntryDto());
        createANewAccountingEntry(getAccountingEntryDto());

        mvc.perform(get("/lancamentos-contabeis/_stats/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.soma", is(2000)))
                .andExpect(jsonPath("$.min", is(1000)))
                .andExpect(jsonPath("$.max", is(1000)))
                .andExpect(jsonPath("$.media", is(1000)))
                .andExpect(jsonPath("$.qtde", is(2)));
    }


    private String createANewAccountingEntry(AccountingEntryDto accountingEntryDto) throws Exception {
        return mvc.perform(post("/lancamentos-contabeis")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(new Gson().toJson(accountingEntryDto))).andReturn().getResponse().getContentAsString();
    }

    private AccountingEntryDto getAccountingEntryDto() {
        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();

        accountingEntryDto.setValor(new BigDecimal(1000));
        accountingEntryDto.setData("2019-01-01");

        AccountDto accountDto = new AccountDto();
        accountDto.setNumero(1000L);

        accountingEntryDto.setContaContabil(accountDto);

        return accountingEntryDto;
    }

}
