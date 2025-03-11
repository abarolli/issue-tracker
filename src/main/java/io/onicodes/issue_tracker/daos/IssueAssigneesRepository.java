package io.onicodes.issue_tracker.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssigneeId;

public interface IssueAssigneesRepository extends JpaRepository<IssueAssignee, IssueAssigneeId> {

}
