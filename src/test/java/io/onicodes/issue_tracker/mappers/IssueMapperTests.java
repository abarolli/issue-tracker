package io.onicodes.issue_tracker.mappers;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;
import io.onicodes.issue_tracker.dtos.issue.IssueCreateRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.models.User;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issue.IssuePriority;
import io.onicodes.issue_tracker.models.issue.IssueStatus;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;


@Slf4j
public class IssueMapperTests {
    
    private Issue getIssue() {
        Issue issue = new Issue();
        issue.setId(Long.valueOf(1));
        issue.setTitle("Title");
        issue.setDescription("Desc");
        issue.setStatus(IssueStatus.OPEN);
        issue.setPriority(IssuePriority.CRITICAL);
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        return issue;
    }

    public IssueCreateRequestDTO getIssueRequestDTO() {
        IssueCreateRequestDTO issueDTO = new IssueCreateRequestDTO();
        issueDTO.setTitle("Title");
        issueDTO.setDescription("Desc");
        issueDTO.setStatus("OPEN");
        issueDTO.setPriority("CRITICAL");
        return issueDTO;
    }


    private User getUser() {
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setName("Oni");
        user.setEmail("myemail@gmail.com");
        return user;
    }

    
    private void assignUserToIssue(User user, Issue issue) {
        IssueAssignee assignee = new IssueAssignee(issue, user);
        Set<IssueAssignee> assignees = Set.of(assignee);
        issue.setAssignees(assignees);
        user.setIssues(assignees);
    }

 
    private void assertIssueResponseDTOMapsIssue(IssueResponseDTO issueDTO, Issue issue) {
        assert(issueDTO != null);
        assert(issueDTO.getTitle() == issue.getTitle());
        assert(issueDTO.getStatus() == issue.getStatus().name());
        assert(issueDTO.getPriority() == issue.getPriority().name());
        assert(issueDTO.getDescription() == issue.getDescription());
        assert(issueDTO.getCreatedAt() == issue.getCreatedAt());
        assert(issueDTO.getUpdatedAt() == issue.getUpdatedAt());
        var users = issue.getAssignees()
                        .stream()
                        .map(a -> IssueMapper.INSTANCE.userToUserDTO(a.getUser()))
                        .collect(Collectors.toList());
        assert(issueDTO.getAssignees().equals(users));        
    }

    private void assertIssueRequestDTOMapsIssue(IssueCreateRequestDTO issueDTO, Issue issue) {
        assert(issueDTO != null);
        assert(issueDTO.getTitle() == issue.getTitle());
        assert(issueDTO.getStatus() == issue.getStatus().name());
        assert(issueDTO.getPriority() == issue.getPriority().name());
        assert(issueDTO.getDescription() == issue.getDescription());
    }

    @Test
    public void shouldMapIssueToIssueResponseDTO() {
        
        var issue = getIssue();
        var user = getUser();
        assignUserToIssue(user, issue);

        IssueResponseDTO issueDTO = IssueMapper.INSTANCE.issueToIssueDTO(issue);
        assertIssueResponseDTOMapsIssue(issueDTO, issue);
    }


    @Test
    public void shouldMapIssueRequestDTOToIssue() {

        var issueDTO = getIssueRequestDTO();
        Issue issue = IssueMapper.INSTANCE.issueCreateRequestDTOToIssue(issueDTO);
        assertIssueRequestDTOMapsIssue(issueDTO, issue);
    }
}
