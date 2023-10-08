package com.fitTracker.fit.mapper.user;

import com.fitTracker.fit.dto.user.UserDto;
import com.fitTracker.fit.model.user.User;
import org.mapstruct.*;

@Mapper(uses = UserDetailsMapper.class, builder = @Builder(disableBuilder = true))
public abstract class UserMapper {
    public abstract User toModel(UserDto userDto);
    public abstract UserDto toDto(User user);
}
