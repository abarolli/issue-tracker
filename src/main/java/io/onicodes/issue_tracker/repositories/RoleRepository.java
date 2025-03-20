package io.onicodes.issue_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.appUser.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
