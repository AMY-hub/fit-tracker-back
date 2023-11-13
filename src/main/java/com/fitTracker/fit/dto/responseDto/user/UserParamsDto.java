package com.fitTracker.fit.dto.responseDto.user;

import com.fitTracker.fit.model.Enum.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserParamsDto implements Serializable {

    @Min(value = 30, message = "Weight should not be less than 30")
    @Max(value = 300, message = "Weight should not be greater than 300")
    private Double initialWeight;

    @Min(value = 30, message = "Weight should not be less than 30")
    @Max(value = 300, message = "Weight should not be greater than 300")
    private Double targetWeight;

    private Gender gender;

    @Past()
    private OffsetDateTime dateOfBirth;

    @Min(value = 100, message = "Height should not be less than 100")
    @Max(value = 260, message = "Height should not be greater than 260")
    private String height;
}
