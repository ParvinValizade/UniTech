package com.unitech.unitech.dto.converter;

import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserToUserDtoConverter {

    private final ModelMapper mapper;

    public UserToUserDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserDto convert(User user){
        return mapper.map(user,UserDto.class);
    }

    public List<UserDto> convert(List<User> userList){
        return userList.stream()
                .map(this::convert).collect(Collectors.toList());
    }

}
