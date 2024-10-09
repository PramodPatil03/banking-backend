package com.banking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePassDto {
    private long acNumber;
    private String oldPassword;
    private String newPassword;
}
