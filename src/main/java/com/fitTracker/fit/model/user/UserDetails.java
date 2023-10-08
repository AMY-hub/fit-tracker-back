package com.fitTracker.fit.model.user;

import com.fitTracker.fit.model.BaseIdentity;
import com.fitTracker.fit.model.Enum.Gender;
import com.fitTracker.fit.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails extends BaseIdentity {

    private Double initialWeight;

    private Double targetWeight;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String height;

    private OffsetDateTime dateOfBirth;

    @OneToOne(mappedBy = "details")
    private User user;
}
