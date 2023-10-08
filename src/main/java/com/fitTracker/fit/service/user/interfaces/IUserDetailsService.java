package com.fitTracker.fit.service.user.interfaces;

import com.fitTracker.fit.dto.user.UserDetailsDto;

public interface IUserDetailsService {
     UserDetailsDto saveOrUpdate(UserDetailsDto userDetailsDto);
}
