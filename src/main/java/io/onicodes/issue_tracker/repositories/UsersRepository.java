package io.onicodes.issue_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {

}
