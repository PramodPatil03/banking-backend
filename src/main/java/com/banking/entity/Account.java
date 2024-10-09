package com.banking.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number", nullable = false, unique = true)
    private long acNumber;

    @Column(name = "acc_holder_name")
    private String acHolderName;

    @Column(name = "balance")
    private long balance;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password" )
    private String password;

}
