package com.fitTracker.fit.mapper.user;

import com.fitTracker.fit.dto.responseDto.user.UserParamsDto;
import com.fitTracker.fit.model.user.UserParams;
import org.mapstruct.*;

@Mapper(builder = @Builder(disableBuilder = true))
public interface UserParamsMapper {
    UserParams toModel(UserParamsDto userDetailsDto);
    UserParamsDto toDto(UserParams userDetails);

    void updateFromDto(UserParamsDto userDetailsDto, @MappingTarget UserParams userDetails);
}
