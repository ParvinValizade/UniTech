package com.unitech.unitech.dto.converter;

import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter {

    private final ModelMapper mapper;

    public UserToUserDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserDto convert(User from){
        return mapper.map(from,UserDto.class);
    }
}
