package com.fitTracker.fit.dto.requestDto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RestorePasswordDto {
    @Size(min= 6, message = "Password must be more than 5 characters")
    @NotEmpty(message = "Password can not be empty")
    private String newPassword;

    @Size(min= 6, message = "Password must be more than 5 characters")
    @NotEmpty(message = "Password can not be empty")
    private String repeatPassword;

    @NotEmpty(message = "Token can not be empty")
    private String token;
}
