package com.fitTracker.fit.dto.responseDto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fitTracker.fit.model.Enum.ErrorType;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ErrorDto {

    private int code;

    private ErrorType type;

    private String message;

    private OffsetDateTime date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<String, String>> errors;
}
