package com.fitTracker.fit.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChangePasswordDto {

    private String oldPassword;

    private String newPassword;

}
