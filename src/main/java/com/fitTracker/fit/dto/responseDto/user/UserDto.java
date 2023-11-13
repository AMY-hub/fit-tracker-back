package com.fitTracker.fit.dto.responseDto.user;

import com.fitTracker.fit.model.Enum.UserRole;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto implements Serializable {
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
    private UserParamsDto details;
}
