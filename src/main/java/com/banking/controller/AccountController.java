package com.banking.controller;


import com.banking.dto.AccountDto;
import com.banking.dto.ChangePassDto;
import com.banking.dto.LoginDto;
import com.banking.dto.TransferDto;
import com.banking.service.AccountServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@CrossOrigin
@NoArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        System.out.println("Create Account");
        AccountDto createdAccount = accountServices.createAccount(accountDto);
        if(createdAccount !=null){
            createdAccount.setPassword("");
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.TOO_MANY_REQUESTS);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AccountDto> login(@RequestBody LoginDto loginDto){
        System.out.println("Got login hit");
        AccountDto response = accountServices.login(loginDto);
        if(response!=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") long acNumber){
        AccountDto accountDto = accountServices.getAccount(acNumber);
        if(accountDto !=null){
            return new ResponseEntity<>(accountDto, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferDto transferDto){
        String res = accountServices.transferMoney(transferDto);
        if(res.equals("insufficient_balance")){
            return new  ResponseEntity<>("Transaction Failed", HttpStatus.NOT_ACCEPTABLE);
        }else if(res.equals("password_mismatch")){
            return new  ResponseEntity<>("Transaction Failed", HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity<>("Transaction Success", HttpStatus.ACCEPTED);
    }

    @PutMapping("/changepass")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassDto changePassDto){
        String res =accountServices.changePassword(changePassDto);
        if(res.equals("failed")){
            return new ResponseEntity<>("Failed",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Success",HttpStatus.ACCEPTED);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody LoginDto loginDto){
        String res = accountServices.deleteAccount(loginDto);
        if(res.equals("failed")){
            return new ResponseEntity<>("Failed",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Success",HttpStatus.ACCEPTED);
    }
}
