package com.fitTracker.fit.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthInfoDto {

     private UserDto user;

     private TokenPairDto tokenPair;

}
