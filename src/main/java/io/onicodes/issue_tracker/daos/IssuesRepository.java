package io.onicodes.issue_tracker.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.Issue;

public interface IssuesRepository extends JpaRepository<Issue, Long> {

}
