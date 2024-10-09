package com.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransferDto {
    private LoginDto loginDto;
    private long acNumber;
    private long amount;
}
