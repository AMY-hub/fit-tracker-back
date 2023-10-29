package com.fitTracker.fit.service.user.impl;

import com.fitTracker.fit.dto.user.UserParamsDto;
import com.fitTracker.fit.exception.UserNotFoundException;
import com.fitTracker.fit.mapper.user.UserParamsMapper;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.model.user.UserParams;
import com.fitTracker.fit.repository.user.UserParamsRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.service.user.interfaces.IUserParamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserParamsService implements IUserParamsService {
    private final UserRepository userRepository;
    private final UserParamsRepository userDetailsRepository;
    private final UserParamsMapper userDetailsMapper;
    @Override
    public UserParams saveOrUpdate(Long userId, UserParamsDto userDetailsDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("ID", userId.toString()));

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
