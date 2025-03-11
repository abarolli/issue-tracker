package io.onicodes.issue_tracker.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {

}
