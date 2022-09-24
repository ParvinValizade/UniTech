package com.unitech.unitech.controller;

import com.unitech.unitech.dto.AccountDto;
import com.unitech.unitech.dto.request.CreateAccountRequest;
import com.unitech.unitech.dto.request.TransferDetailsRequest;
import com.unitech.unitech.dto.request.UpdateAccountStatusRequest;
import com.unitech.unitech.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createAccount")
    @Operation(summary = "Create Account", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest request){
        return new ResponseEntity<>(accountService.createAccount(request), HttpStatus.CREATED);
    }

    @PutMapping("/updateAccountStatus/{accountId}")
    @Operation(summary = "Update Account Status", security = @SecurityRequirement(name = "bearerAuth"))
   public ResponseEntity<AccountDto> updateAccountStatus(@PathVariable String accountId,
                                                         @RequestParam UpdateAccountStatusRequest request){
        return ResponseEntity.ok(accountService.updateAccountStatus(accountId,request));
   }

   @GetMapping("getAllActiveAccounts/{userId}")
   @Operation(summary = "Find All Active Accounts of User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<AccountDto>> getAllActiveAccounts(@PathVariable String userId){
        return ResponseEntity.ok(accountService.getAllActiveAccountsOfUser(userId));
   }

   @PutMapping("transferMoney/{fromAccountId}")
   @Operation(summary = "Transfer Money", security = @SecurityRequirement(name = "bearerAuth"))
   public ResponseEntity<String> transferMoney(@PathVariable String fromAccountId,
                                             @RequestBody TransferDetailsRequest request){
        return ResponseEntity.ok(accountService.transferMoney(fromAccountId,request));
   }
}
