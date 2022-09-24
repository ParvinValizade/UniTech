package com.unitech.unitech.dto.converter;

import com.unitech.unitech.dto.AccountDto;
import com.unitech.unitech.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountToAccountDtoConverter {

    private final ModelMapper mapper;

    public AccountToAccountDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public AccountDto convert(Account account){
        return mapper.map(account,AccountDto.class);
    }

    public List<AccountDto> convert(List<Account> accountList){
        return accountList.stream()
                .map(this::convert).collect(Collectors.toList());
    }
}
