package com.fitTracker.fit.repository.user;

import com.fitTracker.fit.model.user.UserParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserParamsRepository extends JpaRepository<UserParams, Long> {
}
