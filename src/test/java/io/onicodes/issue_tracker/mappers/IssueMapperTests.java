package io.onicodes.issue_tracker.mappers;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDto;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDto;
import io.onicodes.issue_tracker.entityToDtoMappers.AppUserMapper;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.models.appUser.AppUser;
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

    public IssueRequestDto getIssueRequestDto() {
        IssueRequestDto issueDto = new IssueRequestDto();
        issueDto.setTitle("Title");
        issueDto.setDescription("Desc");
        issueDto.setStatus("OPEN");
        issueDto.setPriority("CRITICAL");
        return issueDto;
    }

    public IssueRequestDto getIssueUpdateRequestDto() {
        IssueRequestDto issueDto = new IssueRequestDto();
        issueDto.setTitle("New Title");
        issueDto.setDescription("New Desc");
        issueDto.setStatus("CLOSED");
        issueDto.setPriority("HIGH");
        return issueDto;
    }


    private AppUser getUser() {
        AppUser user = new AppUser();
        user.setId(Long.valueOf(1));
        user.setName("Oni");
        user.setEmail("myemail@gmail.com");
        return user;
    }

    
    private void assignUserToIssue(AppUser user, Issue issue) {
        IssueAssignee assignee = new IssueAssignee(issue, user);
        Set<IssueAssignee> assignees = Set.of(assignee);
        issue.setAssignees(assignees);
        user.setIssues(assignees);
    }

 
    private void assertIssueResponseDtoMapsIssue(IssueResponseDto issueDto, Issue issue) {
        assert(issueDto != null);
        assert(issueDto.getTitle() == issue.getTitle());
        assert(issueDto.getStatus() == issue.getStatus().name());
        assert(issueDto.getPriority() == issue.getPriority().name());
        assert(issueDto.getDescription() == issue.getDescription());
        assert(issueDto.getCreatedAt() == issue.getCreatedAt());
        assert(issueDto.getUpdatedAt() == issue.getUpdatedAt());
        var users = issue.getAssignees()
                        .stream()
                        .map(a -> AppUserMapper.INSTANCE.appUserToAppUserDto(a.getUser()))
                        .collect(Collectors.toList());
        assert(issueDto.getAssignees().equals(users));        
    }

    private void assertIssueRequestDtoMapsIssue(IssueRequestDto issueDto, Issue issue) {
        assert(issueDto != null);
        assert(issueDto.getTitle() == issue.getTitle());
        assert(issueDto.getStatus() == issue.getStatus().name());
        assert(issueDto.getPriority() == issue.getPriority().name());
        assert(issueDto.getDescription() == issue.getDescription());
    }

    @Test
    public void shouldMapIssueToIssueResponseDto() {
        
        var issue = getIssue();
        var user = getUser();
        assignUserToIssue(user, issue);

        IssueResponseDto issueDto = IssueMapper.INSTANCE.issueToIssueDto(issue);
        assertIssueResponseDtoMapsIssue(issueDto, issue);
    }


    @Test
    public void shouldMapIssueRequestDtoToIssue() {

        var issueDto = getIssueRequestDto();
        Issue issue = IssueMapper.INSTANCE.issueRequestDtoToIssue(issueDto);
        assertIssueRequestDtoMapsIssue(issueDto, issue);
    }

    @Test
    public void shouldUpdateIssueWithDto() {

        var issueDto = getIssueUpdateRequestDto();
        var issue = getIssue();
        IssueMapper.INSTANCE.updateFromDto(issueDto, issue);
        assertIssueRequestDtoMapsIssue(issueDto, issue);
    }
}
