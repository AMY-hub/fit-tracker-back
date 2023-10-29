package com.fitTracker.fit.model.user;

import com.fitTracker.fit.model.BaseIdentity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends BaseIdentity {
    @Enumerated(EnumType.STRING)
    private com.fitTracker.fit.model.Enum.UserRole role;
}
