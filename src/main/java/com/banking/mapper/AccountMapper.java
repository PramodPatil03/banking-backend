package com.banking.mapper;

import com.banking.dto.AccountDto;
import com.banking.entity.Account;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountMapper {

    // @Autowired
    // private static PasswordEncoder passwordEncoder;

    public static AccountDto mapToAccountDto(Account account){
        return new AccountDto(
                account.getAcNumber(),
                account.getAcHolderName(),
                account.getBalance(),
                account.getEmail(),
                account.getPassword()
        );
    }

    public static Account mapToAccount(AccountDto accountDto){
        return new Account(
          accountDto.getAcNumber(),
          accountDto.getAcHolderName(),
          accountDto.getBalance(),
          accountDto.getEmail(),
          accountDto.getPassword()
        );
    }
}
