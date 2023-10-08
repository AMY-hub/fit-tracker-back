package com.fitTracker.fit.dto.user;

import com.fitTracker.fit.model.Enum.Gender;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegistrationDto {
    private String name;

    private String email;

    private String password;

    private Double initialWeight;

    private Double targetWeight;

    private Gender gender;

    private OffsetDateTime dateOfBirth;

    private String height;

//    private UserDetailsDto details;
}
