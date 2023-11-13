package com.fitTracker.fit.service.user.impl;

import com.fitTracker.fit.dto.responseDto.user.UserParamsDto;
import com.fitTracker.fit.exception.NotFoundException;
import com.fitTracker.fit.mapper.user.UserParamsMapper;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.model.user.UserParams;
import com.fitTracker.fit.repository.user.UserParamsRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.service.user.interfaces.UserParamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.fitTracker.fit.util.ErrorTemplates.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserParamsServiceImpl implements UserParamsService {
    private final UserRepository userRepository;
    private final UserParamsRepository userDetailsRepository;
    private final UserParamsMapper userDetailsMapper;
    @Override
    public UserParams saveOrUpdate(Long userId, UserParamsDto userDetailsDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "ID", userId.toString()));

        if(Objects.isNull(user.getDetails())) {
            UserParams userDetails = userDetailsMapper.toModel(userDetailsDto);
            userDetails.setUser(user);
            UserParams savedDetails = userDetailsRepository.saveAndFlush(userDetails);
            user.setDetails(userDetails);
            return savedDetails;
        } else {
            UserParams oldDetails = user.getDetails();
            userDetailsMapper.updateFromDto(userDetailsDto, oldDetails);
            userDetailsRepository.saveAndFlush(oldDetails);
            return oldDetails;
        }
    }
}
