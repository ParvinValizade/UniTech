package com.unitech.unitech.service;

import com.unitech.unitech.dto.AccountDto;
import com.unitech.unitech.dto.converter.AccountToAccountDtoConverter;
import com.unitech.unitech.dto.request.CreateAccountRequest;
import com.unitech.unitech.dto.request.TransferDetailsRequest;
import com.unitech.unitech.dto.request.UpdateAccountStatusRequest;
import com.unitech.unitech.exception.*;
import com.unitech.unitech.model.Account;
import com.unitech.unitech.model.AccountStatus;
import com.unitech.unitech.model.User;
import com.unitech.unitech.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final AccountToAccountDtoConverter converter;

    private final UserService userService;

    public AccountService(AccountRepository repository, AccountToAccountDtoConverter converter, UserService userService) {
        this.repository = repository;
        this.converter = converter;
        this.userService = userService;
    }

    public AccountDto createAccount(CreateAccountRequest request){
        User user = userService.findUserById(request.getUserId());
        Account account = new Account(
                request.getInitialCredit(),
                LocalDateTime.now(),
                AccountStatus.ACTIVE,
                user);

        return converter.convert(repository.save(account));
    }

    public AccountDto updateAccountStatus(String accountId,UpdateAccountStatusRequest request){
        Account account = findAccountById(accountId);
        account.setStatus(AccountStatus.valueOf(request.name()));

        return converter.convert(repository.save(account));
    }

    public List<AccountDto> getAllActiveAccountsOfUser(String userId){
        User user = userService.findUserById(userId);
        List<Account> accountList =
                repository.findAllByStatusEqualsAndUserId(AccountStatus.ACTIVE,user);
        return converter.convert(accountList);
    }

    @Transactional
    public String transferMoney(String fromAccountId,TransferDetailsRequest request){
        Account from = findAndCheckAccountActiveOrNot(fromAccountId);
        Account destination = findAndCheckAccountActiveOrNot(request.getDestinationAccountId());
        checkFromAndDestinationAccountsAreSameOrNot(fromAccountId,request.getDestinationAccountId());
        checkAccountBalance(from.getBalance(),request.getAmount());
        from.setBalance(from.getBalance().subtract(request.getAmount()));
        repository.save(from);
        destination.setBalance(destination.getBalance().add(request.getAmount()));
        repository.save(destination);
        return "Transfer completed successfully";
    }


    private Account findAccountById(String accountId){
        return repository.findById(accountId)
                .orElseThrow(()->
                        new AccountNotFoundException("Account couldn't be found by following id: "+accountId));
    }

    private Account findAndCheckAccountActiveOrNot(String accountId){
        findAccountById(accountId);
        return repository.findByIdAndStatusEquals(accountId,AccountStatus.ACTIVE)
                .orElseThrow(()->
                        new AccountIsDeactiveException("Account is deactive with accountId: "+accountId));
    }

    private void checkFromAndDestinationAccountsAreSameOrNot(String fromAccountId,String destinationAccountId){
        if (fromAccountId.equals(destinationAccountId)){
            throw new FromAndDestinationAccountsAreSameException("You cannot send money to the same account!");
        }
    }

    private void checkAccountBalance(BigDecimal balance,BigDecimal transferAmount){
        if (balance.compareTo(transferAmount)<0){
            throw new BalanceNotEnoughException("Not have "+transferAmount+" in account, balance:"+balance);
        }
    }
}
