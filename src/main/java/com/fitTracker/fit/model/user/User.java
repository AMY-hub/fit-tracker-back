package com.fitTracker.fit.model.user;

import com.fitTracker.fit.model.BaseIdentity;
import com.fitTracker.fit.model.Enum.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseIdentity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_details", referencedColumnName = "id")
    private UserDetails details;
}
