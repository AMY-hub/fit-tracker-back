package com.fitTracker.fit.mapper.user;

import com.fitTracker.fit.dto.user.RegistrationDto;
import com.fitTracker.fit.dto.user.UserDto;
import com.fitTracker.fit.model.user.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = UserParamsMapper.class)
public interface UserMapper {
    User toModel(UserDto userDto);
    User toModel(RegistrationDto userDto);
    @Mapping(target = "role", source = "role.role")
    UserDto toDto(User user);
    List<UserDto> toDtos(List<User> users);
}
