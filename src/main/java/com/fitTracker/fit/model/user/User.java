package com.fitTracker.fit.model.user;

import com.fitTracker.fit.model.BaseIdentity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseIdentity implements org.springframework.security.core.userdetails.UserDetails {

    @Column(nullable = false)
    private String firstname;

    @Column
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole role;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private UserParams details;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(getRole().getRole().getValue()));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
