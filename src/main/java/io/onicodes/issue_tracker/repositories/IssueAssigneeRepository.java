package io.onicodes.issue_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssigneeId;

public interface IssueAssigneeRepository extends JpaRepository<IssueAssignee, IssueAssigneeId> {

}
