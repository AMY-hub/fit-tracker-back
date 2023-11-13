package com.fitTracker.fit.dto.requestDto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RequestResetPasswordDto {
    @Email(message = "Not valid email")
    @NotEmpty(message = "Email can not be empty")
    private String email;
}
