package com.fitTracker.fit.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegistrationDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private UserParamsDto details;
}
