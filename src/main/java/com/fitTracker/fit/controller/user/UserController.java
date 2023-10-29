package com.fitTracker.fit.controller.user;

import com.fitTracker.fit.dto.user.UserParamsDto;
import com.fitTracker.fit.dto.user.UserDto;
import com.fitTracker.fit.mapper.user.UserParamsMapper;
import com.fitTracker.fit.mapper.user.UserMapper;
import com.fitTracker.fit.service.user.impl.UserParamsService;
import com.fitTracker.fit.service.user.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserParamsService userDetailsService;
    private final UserParamsMapper userDetailsMapper;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.getById(id)));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<UserParamsDto> saveUserDetails(@PathVariable("id") Long id, @RequestBody UserParamsDto userDetailsDto) {
        return ResponseEntity.ok(userDetailsMapper.toDto(userDetailsService.saveOrUpdate(id, userDetailsDto)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userMapper.toDtos(userService.findAll()));
    }
}
