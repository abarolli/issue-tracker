package io.onicodes.issue_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.appUser.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByUsername(String username);
}
