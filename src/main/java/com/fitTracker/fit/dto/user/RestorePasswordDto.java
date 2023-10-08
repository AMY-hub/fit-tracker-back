package com.fitTracker.fit.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RestorePasswordDto {

    private String newPassword;

    private String repeatPassword;

    private String token;
}
