package com.fitTracker.fit.util;

import com.fitTracker.fit.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenResolver {
    private final HttpServletRequest request;
    public String getTokenFromRequest() {
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Could not resolve authorization token");
        }

        return authHeader.split(" ")[1].trim();
    }
}
