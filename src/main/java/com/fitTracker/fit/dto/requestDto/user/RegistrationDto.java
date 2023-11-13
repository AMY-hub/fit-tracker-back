package com.fitTracker.fit.dto.requestDto.user;

import com.fitTracker.fit.dto.responseDto.user.UserParamsDto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegistrationDto {
    @Size(min= 2, max = 10, message = "Firstname must be between 2 and 10 characters")
    @NotEmpty(message = "Firstname can not be empty")
    private String firstname;

    @Size(min= 2, max = 10, message = "Lastname must be between 2 and 10 characters")
    private String lastname;

    @Email(message = "Not valid email")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @Size(min= 6, message = "Password must be more than 5 characters")
    @NotEmpty(message = "Password can not be empty")
    private String password;

    private UserParamsDto details;
}
