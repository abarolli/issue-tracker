package io.onicodes.issue_tracker.models.issueAssignee;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class IssueAssigneeId implements Serializable {
    private Long issueId;
    private Long userId;
}