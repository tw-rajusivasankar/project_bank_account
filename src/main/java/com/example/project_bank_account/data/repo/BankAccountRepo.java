package com.example.project_bank_account.data.repo;

import com.example.project_bank_account.data.domain.BankAccount;
import com.example.project_bank_account.model.response.BankAccountLoginResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, UUID> {

    @Query("SELECT u FROM BankAccount u WHERE u.uuid = :userId AND u.userPassword = :password")
    Optional<BankAccount> findByUserIdAndPassword(@Param("userId") UUID userId, @Param("password") String password);

    boolean existsByBankAccountNumber(String bankAccountNumber);

    Optional<BankAccount> findByBankAccountNumber(String bankAccountNumber);
}