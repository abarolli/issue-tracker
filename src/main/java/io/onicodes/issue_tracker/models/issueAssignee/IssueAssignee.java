package io.onicodes.issue_tracker.models.issueAssignee;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.onicodes.issue_tracker.models.User;
import io.onicodes.issue_tracker.models.issue.Issue;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "issue_assignees")
public class IssueAssignee {
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private IssueAssigneeId id;

    @ManyToOne
    @MapsId("issueId")
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    public IssueAssignee(Issue issue, User user) {
        this.issue = issue;
        this.user = user;
        this.id = new IssueAssigneeId(issue.getId(), user.getId());
    }
}
