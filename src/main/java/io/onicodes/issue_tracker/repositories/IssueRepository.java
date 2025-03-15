package io.onicodes.issue_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.issue.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {

}
