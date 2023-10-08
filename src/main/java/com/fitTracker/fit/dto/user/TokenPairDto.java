package com.fitTracker.fit.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TokenPairDto {
    private String accessToken;

    private String refreshToken;
}
