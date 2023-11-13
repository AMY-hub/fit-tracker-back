package com.fitTracker.fit.controller.user;

import com.fitTracker.fit.dto.requestDto.user.ChangePasswordDto;
import com.fitTracker.fit.dto.requestDto.user.RequestResetPasswordDto;
import com.fitTracker.fit.dto.responseDto.user.AuthResultDto;
import com.fitTracker.fit.dto.responseDto.user.UserParamsDto;
import com.fitTracker.fit.dto.responseDto.user.UserDto;
import com.fitTracker.fit.mapper.user.UserParamsMapper;
import com.fitTracker.fit.mapper.user.UserMapper;
import com.fitTracker.fit.service.user.impl.UserParamsServiceImpl;
import com.fitTracker.fit.service.user.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;
    private final UserParamsServiceImpl userDetailsService;
    private final UserParamsMapper userDetailsMapper;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.getById(id)));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<UserParamsDto> saveUserDetails(@PathVariable("id") Long id, @RequestBody @Valid UserParamsDto userDetailsDto) {
        return ResponseEntity.ok(userDetailsMapper.toDto(userDetailsService.saveOrUpdate(id, userDetailsDto)));
    }

    @PutMapping("/password")
    public ResponseEntity<AuthResultDto> changeUserPassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(userService.changePassword(changePasswordDto));
    }

    @PostMapping("/request-reset")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody @Valid RequestResetPasswordDto requestResetPasswordDto) {
        userService.requestPasswordReset(requestResetPasswordDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userMapper.toDtos(userService.findAll()));
    }
}
