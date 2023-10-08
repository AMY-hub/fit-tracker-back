package com.fitTracker.fit.dto.user;

import com.fitTracker.fit.model.Enum.Gender;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDetailsDto implements Serializable {
    private Double initialWeight;

    private Double targetWeight;

    private Gender gender;

    private int age;

    private OffsetDateTime dateOfBirth;

    private String height;
}
