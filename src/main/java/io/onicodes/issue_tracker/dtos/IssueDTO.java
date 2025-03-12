package io.onicodes.issue_tracker.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issue.IssuePriority;
import io.onicodes.issue_tracker.models.issue.IssueStatus;


@Getter
@Setter
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private IssueStatus status;
    private IssuePriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserDTO> assignees;

    public IssueDTO(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.description = issue.getDescription();
        this.status = issue.getStatus();
        this.priority = issue.getPriority();
        this.createdAt = issue.getCreatedAt();
        this.updatedAt = issue.getUpdatedAt();
        this.assignees = issue.getAssignees().stream()
            .map(a -> new UserDTO(a.getUser()))
            .collect(Collectors.toList());
    }
}
