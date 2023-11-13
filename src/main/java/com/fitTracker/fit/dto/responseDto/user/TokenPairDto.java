package com.fitTracker.fit.dto.responseDto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TokenPairDto {
    @NotEmpty(message = "Token can not be empty")
    private String accessToken;

    @NotEmpty(message = "Token can not be empty")
    private String refreshToken;
}
