package com.fitTracker.fit.service.user.interfaces;

import com.fitTracker.fit.dto.user.UserParamsDto;
import com.fitTracker.fit.model.user.UserParams;

public interface IUserParamsService {
     UserParams saveOrUpdate(Long userId, UserParamsDto userDetailsDto);
}
