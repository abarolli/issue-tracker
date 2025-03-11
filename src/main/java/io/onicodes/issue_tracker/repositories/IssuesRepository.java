package io.onicodes.issue_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.issue.Issue;

public interface IssuesRepository extends JpaRepository<Issue, Long> {

}
