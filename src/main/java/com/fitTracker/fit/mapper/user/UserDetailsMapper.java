package com.fitTracker.fit.mapper.user;

import com.fitTracker.fit.dto.user.UserDetailsDto;
import com.fitTracker.fit.model.user.UserDetails;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Date;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class UserDetailsMapper {

    public abstract UserDetails toModel(UserDetailsDto userDetailsDto);
    public abstract UserDetailsDto toDto(UserDetails userDetails);

    @AfterMapping
    protected void setAge(UserDetails userDetails, @MappingTarget UserDetailsDto userDetailsDto) {
        if(userDetails.getDateOfBirth() != null) {
            Period age = Period.between(userDetails.getDateOfBirth().toLocalDate(), LocalDate.now());
            userDetailsDto.setAge(age.getYears());
        }
    }
}
