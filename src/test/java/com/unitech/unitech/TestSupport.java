package com.unitech.unitech;

import com.unitech.unitech.dto.AccountDto;
import com.unitech.unitech.dto.AccountStatusDto;
import com.unitech.unitech.dto.CityDto;
import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.dto.request.CreateAccountRequest;
import com.unitech.unitech.dto.request.CreateUserRequest;
import com.unitech.unitech.dto.request.TransferDetailsRequest;
import com.unitech.unitech.model.Account;
import com.unitech.unitech.model.AccountStatus;
import com.unitech.unitech.model.City;
import com.unitech.unitech.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {

    public CreateUserRequest generateCreateUserRequest(){
        return new CreateUserRequest("Parvin",
                "Valizade",
                LocalDate.parse("2000-10-04"),
                "Yasamal",
                "12345A7",
                "string1@A");
    }

    public CreateUserRequest generateCreateUserRequestWithInvalidPin(){
        return new CreateUserRequest("Parvin",
                "Valizade",
                LocalDate.parse("2000-10-04"),
                "Yasamal",
                "12345A",
                "string1@A");
    }

    public CreateUserRequest generateCreateUserRequestWithInvalidPassword(){
        return new CreateUserRequest("Parvin",
                "Valizade",
                LocalDate.parse("2000-10-04"),
                "Yasamal",
                "12345A7",
                "string1@");
    }

    public User generateUser(){
        return new User(
                "user-id",
                "Parvin",
                "Valizade",
                LocalDate.parse("2000-10-04"),
                City.Baku,
                "Yasamal",
                "12345A7",
                "string1@A",
                LocalDateTime.now(),
                null
        );
    }

    public UserDto generateUserDto(){
       return new UserDto(
               "user-id",
               "Parvin",
               "Valizade",
               LocalDate.parse("2000-10-04"),
               CityDto.Baku,
               "Yasamal"
       );
    }

    public List<User> generateUserList(){
        return IntStream.range(0, 5).mapToObj(i->
                new User("user-id"+i,
                        "Parvin",
                        "Valizade",
                        LocalDate.parse("2000-10-04"),
                        City.Baku,
                        "Yasamal",
                        "12345A"+i,
                        "s1ringA@",
                        LocalDateTime.now(),
                        Set.of())).collect(Collectors.toList());
    }

    public List<UserDto> generateUserDtoList(List<User> userList){
          return  userList.stream()
                  .map(user->new UserDto(
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getBirthDate(),
                            CityDto.valueOf(user.getCity().name()),
                            user.getAddress()
                    )).collect(Collectors.toList());
    }


    public CreateAccountRequest generateCreateAccountRequest(String userId){
        return new CreateAccountRequest(
                new BigDecimal(100),
                userId
        );
    }
    public Account generateAccount(User user){
        return new Account(
              "account-id",
              new BigDecimal(100),
              LocalDateTime.now(),
                AccountStatus.ACTIVE,
                user
        );
    }

    public AccountDto generateAccountDto(LocalDateTime time){
        return new AccountDto(
                "account-id",
                new BigDecimal(100),
                time,
                AccountStatusDto.ACTIVE);
    }

    public Account generateUpdatedAccount(Account account,User user){
        return new Account(account.getId(),account.getBalance(),account.getCreationDate(),
                AccountStatus.DEACTIVE,user);
    }

    public AccountDto generateUpdatedAccountDto(Account updatedAccount){
        return new AccountDto(
                updatedAccount.getId(),
                updatedAccount.getBalance(),
                updatedAccount.getCreationDate(),
                AccountStatusDto.valueOf(updatedAccount.getStatus().name()));
    }

    public List<Account> generateAccountList(User user){
        return IntStream.range(0, 5).mapToObj(
                i->new Account(
                        "account-id"+i,
                        new BigDecimal(100),
                        LocalDateTime.now(),
                        AccountStatus.ACTIVE,
                        user
                )).collect(Collectors.toList());
    }

    public List<AccountDto> generateAccountDtoList(List<Account> accountList){
        return accountList.stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getBalance(),
                        account.getCreationDate(),
                        AccountStatusDto.valueOf(account.getStatus().name())
                )).collect(Collectors.toList());
    }

    public Account generateDestinationAccount(User user){
        return new Account(
                "destinationAccount-id",
                new BigDecimal(200),
                LocalDateTime.now(),
                AccountStatus.ACTIVE,
                user
        );
    }

    public TransferDetailsRequest generateTransferDetailsRequest(){
        return new TransferDetailsRequest("destinationAccount-id",
                new BigDecimal(70));
    }

    public TransferDetailsRequest generateTransferDetailsRequestWithSameAccountId(){
        return new TransferDetailsRequest("account-id",
                new BigDecimal(70));
    }

    public TransferDetailsRequest generateTransferDetailsRequestWithMoreMoneyThanBalance(){
        return new TransferDetailsRequest("destinationAccount-id",
                new BigDecimal(170));
    }

    public Account generateUpdatedFromAccount(Account fromAccount,BigDecimal amount){
        return new Account(
                fromAccount.getId(),
                fromAccount.getBalance().subtract(amount),
                fromAccount.getCreationDate(),
                fromAccount.getStatus(),
                fromAccount.getUserId()
        );
    }

    public Account generateUpdatedDestinationAccount(Account destinationAccount,BigDecimal amount){
        return new Account(
                destinationAccount.getId(),
                destinationAccount.getBalance().add(amount),
                destinationAccount.getCreationDate(),
                destinationAccount.getStatus(),
                destinationAccount.getUserId()
        );
    }

    public Account generateDeactiveAccount(User user){
        return new Account(
                "account-id",
                new BigDecimal(100),
                LocalDateTime.now(),
                AccountStatus.DEACTIVE,
                user
        );
    }

    public Account generateDeactiveDestinationAccount(User user){
        return new Account(
                "destinationAccount-id",
                new BigDecimal(100),
                LocalDateTime.now(),
                AccountStatus.DEACTIVE,
                user
        );
    }

}
