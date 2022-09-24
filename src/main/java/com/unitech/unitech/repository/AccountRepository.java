package com.unitech.unitech.repository;

import com.unitech.unitech.model.Account;
import com.unitech.unitech.model.AccountStatus;
import com.unitech.unitech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,String> {
    List<Account> findAllByStatusEqualsAndUserId(AccountStatus status,User user);
    Optional<Account> findByIdAndStatusEquals(String accountId,AccountStatus status);
}
