package com.fitTracker.fit.controller.user;

import com.fitTracker.fit.dto.user.RegistrationDto;
import com.fitTracker.fit.dto.user.UserDto;
import com.fitTracker.fit.mapper.user.UserMapper;
import com.fitTracker.fit.service.user.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.getById(id)));
    }

    @PostMapping
    public UserDto create(@RequestBody RegistrationDto userData) {
        return userMapper.toDto(userService.create(userData));
    }
}
