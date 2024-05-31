package com.example.project_bank_account.data.repo;

import com.example.project_bank_account.data.domain.BankAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionRepo extends JpaRepository<BankAccountTransaction, Long> {
}