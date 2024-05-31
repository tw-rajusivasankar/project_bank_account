package com.example.project_bank_account.data.repo;

import com.example.project_bank_account.data.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, UUID> {

    List<CreditCard> findByBankAccountBankAccountNumber(String bankAccountNumber);

}