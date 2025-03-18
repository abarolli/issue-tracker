package io.onicodes.issue_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
