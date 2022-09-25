package com.unitech.unitech.service;

import com.unitech.unitech.TestSupport;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceTest extends TestSupport {

    private  AccountRepository repository;
    private  AccountToAccountDtoConverter converter;
    private  UserService userService;

    private AccountService accountService;

    private final User user = generateUser();

    @BeforeEach
    public void setUp(){
        repository = mock(AccountRepository.class);
        converter = mock(AccountToAccountDtoConverter.class);
        userService = mock(UserService.class);

        accountService = new AccountService(repository,converter,userService);
    }

    @Test
    void testCreateAccount_whenUserIsExist_itShouldCreateAccount(){
        CreateAccountRequest request = generateCreateAccountRequest(user.getId());
        Account account = generateAccount(user);
        Account savedAccount = generateAccount(user);
        AccountDto expected = generateAccountDto(account.getCreationDate());

        when(userService.findUserById(request.getUserId())).thenReturn(user);
        when(repository.save(account)).thenReturn(savedAccount);
        when(converter.convert(savedAccount)).thenReturn(expected);

        AccountDto result = accountService.createAccount(request);

        assertEquals(expected,result);

        verify(userService).findUserById(request.getUserId());
        verify(repository).save(account);
        verify(converter).convert(savedAccount);

    }

    @Test
    void testCreateAccount_whenUserDoesNotExist_itShouldThrowUserNotFoundException(){
        String userId = "userId";
        CreateAccountRequest request = generateCreateAccountRequest(userId);

        when(userService.findUserById(request.getUserId())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,()->
                accountService.createAccount(request));

        verify(userService).findUserById(request.getUserId());
        verifyNoInteractions(repository);
        verifyNoInteractions(converter);

    }

    @Test
    void testUpdateAccountStatus_whenAccountIdIsExist_itShouldReturnAccountDto(){
        Account account = generateAccount(user);
        Account updatedAccount = generateUpdatedAccount(account,user);
        AccountDto updatedAccountDto = generateUpdatedAccountDto(updatedAccount);

        when(repository.findById(account.getId())).thenReturn(Optional.of(account));
        when(repository.save(account)).thenReturn(updatedAccount);
        when(converter.convert(updatedAccount)).thenReturn(updatedAccountDto);

        AccountDto result = accountService.updateAccountStatus(account.getId(), UpdateAccountStatusRequest.DEACTIVE);

        assertEquals(updatedAccountDto,result);

        verify(repository).findById(account.getId());
        verify(repository).save(account);
        verify(converter).convert(updatedAccount);

    }

    @Test
    void testUpdateAccountStatus_whenAccountIdDoesNotExist_itShouldThrowAccountNotFoundException(){
        String accountId = "accountId";

        when(repository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,()->
                accountService.updateAccountStatus(accountId, UpdateAccountStatusRequest.DEACTIVE));


        verify(repository).findById(accountId);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    void testGetAllActiveAccountsOfUser_whenUserIdIsExist_itShouldReturnAccountDtoList(){
        List<Account> accountList = generateAccountList(user);
        List<AccountDto> accountDtoList = generateAccountDtoList(accountList);

        when(userService.findUserById(user.getId())).thenReturn(user);
        when(repository.findAllByStatusEqualsAndUserId(AccountStatus.ACTIVE,user)).thenReturn(accountList);
        when(converter.convert(accountList)).thenReturn(accountDtoList);

        List<AccountDto> resultList = accountService.getAllActiveAccountsOfUser(user.getId());

        assertEquals(accountDtoList,resultList);

        verify(userService).findUserById(user.getId());
        verify(repository).findAllByStatusEqualsAndUserId(AccountStatus.ACTIVE,user);
        verify(converter).convert(accountList);
    }

    @Test
    void testGetAllActiveAccountsOfUser_whenUserIdDoesNotExist_itShouldThrowUserNotFoundException(){
        String userId = "userId";

        when(userService.findUserById(userId)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,()->
                accountService.getAllActiveAccountsOfUser(userId));

        verify(userService).findUserById(userId);
        verifyNoInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    void testTransferMoney_whenEverythingIsOkay_itShouldReturnSuccessMessage(){
        String fromAccountId = "account-id";
        TransferDetailsRequest request = generateTransferDetailsRequest();
        Account from = generateAccount(user);
        Account destination = generateDestinationAccount(user);
        Account fromAccount = generateUpdatedFromAccount(from,request.getAmount());
        Account destinationAccount = generateUpdatedDestinationAccount(destination,request.getAmount());

        String expectedSuccessMessage = "Transfer completed successfully";


        when(repository.findById(fromAccountId)).thenReturn(Optional.of(from));
        when(repository.findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE))
                .thenReturn(Optional.of(from));
        when(repository.findById(request.getDestinationAccountId())).thenReturn(Optional.of(destination));
        when(repository.findByIdAndStatusEquals(request.getDestinationAccountId(),AccountStatus.ACTIVE))
                .thenReturn(Optional.of(destination));
        when(repository.save(from)).thenReturn(fromAccount);
        when(repository.save(destination)).thenReturn(destinationAccount);

        String result = accountService.transferMoney(fromAccountId,request);

        assertEquals(expectedSuccessMessage,result);

        verify(repository).findById(fromAccountId);
        verify(repository).findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE);
        verify(repository).findById(request.getDestinationAccountId());
        verify(repository).findByIdAndStatusEquals(request.getDestinationAccountId(),AccountStatus.ACTIVE);
        verify(repository).save(from);
        verify(repository).save(destination);
    }

    @Test
    void testTransferMoney_whenFromAccountIdDoesNotExist_itShouldThrowAccountNotFoundException(){
        String fromAccountId = "account-id";
        TransferDetailsRequest request = generateTransferDetailsRequest();


        when(repository.findById(fromAccountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,()->
                accountService.transferMoney(fromAccountId,request));

        verify(repository).findById(fromAccountId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testTransferMoney_whenFromAccountIdIsExistButDoesNotActive_itShouldThrowAccountIsDeactiveException(){
        String fromAccountId = "account-id";
        TransferDetailsRequest request = generateTransferDetailsRequest();
        Account deactiveAccount = generateDeactiveAccount(user);


        when(repository.findById(fromAccountId)).thenReturn(Optional.of(deactiveAccount));

        assertThrows(AccountIsDeactiveException.class,()->
                accountService.transferMoney(fromAccountId,request));

        verify(repository).findById(fromAccountId);
        verify(repository).findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testTransferMoney_whenFromAccountIdIsExistAndActiveButDestinationAccountNotFound_itShouldThrowAccountNotFoundException(){
        String fromAccountId = "account-id";
        TransferDetailsRequest request = generateTransferDetailsRequest();
        Account fromAccount = generateAccount(user);


        when(repository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(repository.findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE))
                .thenReturn(Optional.of(fromAccount));
        when(repository.findById(request.getDestinationAccountId())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,()->
                accountService.transferMoney(fromAccountId,request));

        verify(repository).findById(fromAccountId);
        verify(repository).findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE);
        verify(repository).findById(request.getDestinationAccountId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testTransferMoney_whenDestinationAccountIdIsExistButDoesNotActive_itShouldThrowAccountIsDeactiveException(){
        String fromAccountId = "account-id";
        TransferDetailsRequest request = generateTransferDetailsRequest();
        Account fromAccount = generateAccount(user);
        Account deactiveAccount = generateDeactiveDestinationAccount(user);


        when(repository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(repository.findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE))
                .thenReturn(Optional.of(fromAccount));
        when(repository.findById(request.getDestinationAccountId())).thenReturn(Optional.of(deactiveAccount));

        assertThrows(AccountIsDeactiveException.class,()->
                accountService.transferMoney(fromAccountId,request));

        verify(repository).findById(fromAccountId);
        verify(repository).findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE);
        verify(repository).findById(request.getDestinationAccountId());
        verify(repository).findByIdAndStatusEquals(request.getDestinationAccountId(),AccountStatus.ACTIVE);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testTransferMoney_whenFromAndDestinationAccountsAreSame_itShouldThrowFromAndDestinationAccountsAreSameException(){
        String fromAccountId = "account-id";
        TransferDetailsRequest request = generateTransferDetailsRequestWithSameAccountId();
        Account from = generateAccount(user);
        Account destination = generateAccount(user);


        when(repository.findById(fromAccountId)).thenReturn(Optional.of(from));
        when(repository.findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE))
                .thenReturn(Optional.of(from));
        when(repository.findById(request.getDestinationAccountId())).thenReturn(Optional.of(destination));
        when(repository.findByIdAndStatusEquals(request.getDestinationAccountId(),AccountStatus.ACTIVE))
                .thenReturn(Optional.of(destination));

        assertThrows(FromAndDestinationAccountsAreSameException.class,()->
                accountService.transferMoney(fromAccountId,request));
    }

    @Test
    void testTransferMoney_whenFromAccountBalanceNotEnough_itShouldThrowBalanceNotEnoughException(){
        String fromAccountId = "account-id";
        TransferDetailsRequest request = generateTransferDetailsRequestWithMoreMoneyThanBalance();
        Account from = generateAccount(user);
        Account destination = generateDestinationAccount(user);

        when(repository.findById(fromAccountId)).thenReturn(Optional.of(from));
        when(repository.findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE))
                .thenReturn(Optional.of(from));
        when(repository.findById(request.getDestinationAccountId())).thenReturn(Optional.of(destination));
        when(repository.findByIdAndStatusEquals(request.getDestinationAccountId(),AccountStatus.ACTIVE))
                .thenReturn(Optional.of(destination));

        assertThrows(BalanceNotEnoughException.class,()->
                accountService.transferMoney(fromAccountId,request));

        verify(repository).findById(fromAccountId);
        verify(repository).findByIdAndStatusEquals(fromAccountId,AccountStatus.ACTIVE);
        verify(repository).findById(request.getDestinationAccountId());
        verify(repository).findByIdAndStatusEquals(request.getDestinationAccountId(),AccountStatus.ACTIVE);
        verifyNoMoreInteractions(repository);
    }

}
