package com.fitTracker.fit.dto.user;

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
    private String name;

    private String email;

    private UserDetailsDto details;

    private UserRole role;
}
