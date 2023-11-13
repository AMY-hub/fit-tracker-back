package com.fitTracker.fit.service.user.interfaces;

import com.fitTracker.fit.dto.responseDto.user.UserParamsDto;
import com.fitTracker.fit.model.user.UserParams;

public interface UserParamsService {
     UserParams saveOrUpdate(Long userId, UserParamsDto userDetailsDto);
}
