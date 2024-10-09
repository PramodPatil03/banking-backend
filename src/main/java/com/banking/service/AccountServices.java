package com.banking.service;

import com.banking.dto.AccountDto;
import com.banking.dto.ChangePassDto;
import com.banking.dto.LoginDto;
import com.banking.dto.TransferDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AccountServices {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto login(LoginDto loginDto);
    AccountDto getAccount(Long acNumber);
    String transferMoney(TransferDto transferDto);
    String changePassword(ChangePassDto changePassDto);
    String deleteAccount(LoginDto loginDto);
}
