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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.onicodes.issue_tracker.models.appUser.AppUser;
import io.onicodes.issue_tracker.models.issue.Issue;


@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity
@Table(name = "issue_assignees")
public class IssueAssignee {
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private IssueAssigneeId id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @MapsId("issueId")
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    public IssueAssignee(Issue issue, AppUser user) {
        this.issue = issue;
        this.user = user;
        this.id = new IssueAssigneeId(issue.getId(), user.getId());
    }
}
