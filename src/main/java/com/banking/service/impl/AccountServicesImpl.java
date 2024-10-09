package com.banking.service.impl;

import com.banking.dto.AccountDto;
import com.banking.dto.ChangePassDto;
import com.banking.dto.LoginDto;
import com.banking.dto.TransferDto;
import com.banking.entity.Account;
import com.banking.exceptions.NoResourceFoundException;
import com.banking.mapper.AccountMapper;
import com.banking.repository.AccountRepository;
import com.banking.service.AccountServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountServicesImpl implements AccountServices {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AccountDto createAccount(AccountDto accountDto){
            System.out.println("Came here");
            Account account = AccountMapper.mapToAccount(accountDto);
            Account searchedAccount = accountRepository.findUserByEmail(account.getEmail());
            if(searchedAccount !=null) return null;
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            Account createdAccount = accountRepository.save(account);
            return AccountMapper.mapToAccountDto(createdAccount);
    }

    @Override
    public AccountDto login(LoginDto loginDto) {
        long acNumber = loginDto.getAcNumber();
        Account account = accountRepository.findById(acNumber).orElseThrow(()-> new NoResourceFoundException("Not Found"));
            if(account.getAcNumber() == loginDto.getAcNumber() && passwordEncoder.matches(loginDto.getPassword(),account.getPassword())){
                account.setPassword("");
                return AccountMapper.mapToAccountDto(account);
            }else{
                return null;
            }
    }

    @Override
    public AccountDto getAccount(Long acNumber) {
        Account account = accountRepository.findById(acNumber).orElseThrow(()-> new NoResourceFoundException("No account found with Id: "+ acNumber));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public String transferMoney(TransferDto transferDto) {
        LoginDto loginDto = transferDto.getLoginDto();
        long acNumber = transferDto.getAcNumber();
        long amount = transferDto.getAmount();
        if(loginDto.getAcNumber() == acNumber){
            return "Invalid Transactions";
        }
        Account firstAc = accountRepository.findById(loginDto.getAcNumber()).orElseThrow(()-> new NoResourceFoundException("No Account found"));
        Account secondAc = accountRepository.findById(acNumber).orElseThrow(()->new NoResourceFoundException("No account dound"));
        if(passwordEncoder.matches(loginDto.getPassword(), firstAc.getPassword())){
            if(firstAc.getBalance() < amount || firstAc.getBalance() < 1000) {
                return "insufficient_balance";
            }else{
                secondAc.setBalance(secondAc.getBalance() + amount);
                firstAc.setBalance(firstAc.getBalance() - amount);
                accountRepository.save(firstAc);
                accountRepository.save(secondAc);
                return "success";
            }
        }else{
            return "password_mismatch";
        }
    }

    @Override
    public String changePassword(ChangePassDto changePassDto) {
        Account account = accountRepository.findById(changePassDto.getAcNumber()).orElseThrow(()->new NoResourceFoundException("No account found"));
        if(passwordEncoder.matches(changePassDto.getOldPassword(), account.getPassword())){
            account.setPassword(passwordEncoder.encode(changePassDto.getNewPassword()));
            accountRepository.save(account);
            return "success";
        }
        return "failed";
    }

    @Override
    public String deleteAccount(LoginDto loginDto) {
        Account account = accountRepository.findById(loginDto.getAcNumber()).orElseThrow(()-> new NoResourceFoundException("No Account Found"));
        if(passwordEncoder.matches(loginDto.getPassword(), account.getPassword())){
            accountRepository.deleteById(loginDto.getAcNumber());
            return "success";
        }
        return "failed";
    }


}
