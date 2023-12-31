package com.fitTracker.fit.dto.responseDto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthResultDto {

     private UserDto user;

     private TokenPairDto tokenPair;
}
